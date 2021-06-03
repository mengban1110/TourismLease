package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.LeaseMapper;
import cn.doo.code.lease.dao.RepertoryMapper;
import cn.doo.code.lease.dao.TenantMapper;
import cn.doo.code.lease.entity.Leaseinfo;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.LeasePojo;
import cn.doo.code.lease.entity.pojo.LeaseinfoPojo;
import cn.doo.code.lease.entity.pojo.RepertoryPojo;
import cn.doo.code.lease.entity.pojo.TenantPojo;
import cn.doo.code.lease.service.LeaseService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.redis.RedisUtil;
import cn.doo.code.utils.solr.SolrBaseModules;
import cn.doo.code.utils.solr.SolrUtil;
import cn.doo.code.utils.solr.entity.DataEntity;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
//@Transactional(rollbackFor = Exception.class)
public class LeaseServiceimpl implements LeaseService {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;
    private Lock lock = new ReentrantLock();


    @Autowired
    private LeaseMapper leaseMapper;
    @Autowired
    private RedisUtil jedis;
    @Autowired
    private SolrUtil SolrUtil;
    @Autowired
    private RepertoryMapper repertoryMapper;
    @Autowired
    private TenantMapper tenantMapper;


    /**
     * 获取所有租赁订单信息
     *
     * @param tokenVerify
     * @param phone
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Map<String, Object> queryAll(TokenVerify tokenVerify, String phone, Integer page, Integer limit) throws SolrServerException, IOException {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        List<DataEntity> dataEntities = SolrUtil.queryIndexItems(page, limit, phone);
        int count = SolrUtil.queryIndexCount(phone);

        return DooUtils.print(0, "请求成功", dataEntities, count);
    }

    /**
     * @param tokenVerify
     * @param leaseinfo
     * @param uid
     * @return
     * @desc 新增一个订单信息
     */
    @Override
    public Map<String, Object> insertOne(TokenVerify tokenVerify, List<LeaseinfoPojo> leaseinfo, Integer uid) throws Exception {

        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        TenantPojo tenantPojo = tenantMapper.selectById(uid);
        if (tenantPojo == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }


        lock.lock();
        //事务对象
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);

        LeasePojo leasePojo;

        //总押金
        Integer sumDeposit = 0;
        //Leaseinfo对象
        List<Leaseinfo> leaseinfos = new ArrayList<>();
        /**
         * 计算总押金 并 获取Leaseinfos整体对象
         */
        try {
            for (LeaseinfoPojo leaseinfoPojo : leaseinfo) {

                //总押金
                sumDeposit += getDeposit(leaseinfoPojo);

                //获取单个Leaseinfo对象
                Leaseinfo tempLeaseinfo = getLeaseinfo(leaseinfoPojo);
                //添加Leaseinfos对象
                leaseinfos.add(tempLeaseinfo);

                //校验库存数量 并 减少库存数量
                reduceCount(leaseinfoPojo);
            }

            //计算结束 获取总押金 和 leaseinfo Json串
            ObjectMapper objectMapper = new ObjectMapper();
            String leaseinfoJson = objectMapper.writeValueAsString(leaseinfos);

            //创建LeasePojo对象 开始添加数据
            leasePojo = new LeasePojo();
            leasePojo.setLeaseinfo(leaseinfoJson);
            leasePojo.setUid(uid);
            leasePojo.setRent(0);
            leasePojo.setBreakinfo("null");
            leasePojo.setCreatetime(new Date());
            leasePojo.setDeposit(sumDeposit);
            leasePojo.setStatus(0);

            leaseMapper.insert(leasePojo);

            System.out.println("没有异常，准备提交事务");
            platformTransactionManager.commit(transaction);
        } catch (Exception e) {
            System.out.println("有异常，准备回滚事务");
            e.printStackTrace();
            platformTransactionManager.rollback(transaction);
            return DooUtils.print(-2, "参数异常", null, null);
        }finally {
            lock.unlock();
        }

        SolrBaseModules.saveOrUpdate(new DataEntity(leasePojo, tenantMapper.selectById(uid)));

        return DooUtils.print(0, "添加成功", null, null);
    }

    /**
     * 减少库存
     * @param leaseinfoPojo
     * @throws Exception
     */
    private void reduceCount(LeaseinfoPojo leaseinfoPojo) throws Exception {
        RepertoryPojo repertoryPojo = repertoryMapper.selectById(leaseinfoPojo.getId());
        if (repertoryPojo == null) {
            throw new Exception("此产品在库存中不存在");
        } else {
            //要租的数量
            Integer leaseCount = leaseinfoPojo.getCount();
            //现在库存的数量
            Integer repertoryCount = repertoryPojo.getCount();
            if (repertoryCount < leaseCount) {
                throw new Exception("库存数量不够");
            }
            leaseMapper.reduceCount(leaseCount, leaseinfoPojo.getId());
        }
    }


    /**
     * 获取单个Leaseinfo对象
     *
     * @param leaseinfoPojo
     * @return
     * @throws JsonProcessingException
     */
    private Leaseinfo getLeaseinfo(LeaseinfoPojo leaseinfoPojo) throws JsonProcessingException {

        //商品id
        Integer repertoryId = leaseinfoPojo.getId();


        //单个对象信息
        Leaseinfo leaseinfo = new Leaseinfo();
        //设置商品id
        leaseinfo.setId(repertoryId);

        //生成对应uuid
        //获取此商品需要生成多少个uuid
        Integer count = leaseinfoPojo.getCount();
        //uuids容器 存放需要生成的uuid
        List<String> uuids = new ArrayList<>();

        //生成商品uuid
        for (int i = 0; i < count; i++) {
            //生成uuid 长度为6
            String temp = IdUtil.simpleUUID();
            String sub = StrUtil.sub(temp, 0, 6);
            //获取uuid前缀
            String uuidPre = getUuidPre(repertoryId);
            //获取uuid
            String uuid = sub + uuidPre;
            //添加uuid 到 uuids数组
            uuids.add(uuid);
        }
        //存放uuids对象
        leaseinfo.setNumber(uuids);

        return leaseinfo;
    }

    /**
     * 判断商品id是几位数 并返回前缀 eg: 001 010 100
     *
     * @param id
     * @return
     */
    private String getUuidPre(Integer id) {
        //生成uuid策略
        String ids = id + "";
        //获取几位数
        int length = ids.length();
        if (length == 1) {
            //个位数
            ids = "00" + ids;
        } else if (length == 2) {
            //十位数
            ids = "0" + ids;
        }
        return ids;
    }

    /**
     * 获取总押金
     *
     * @param leaseinfoPojo
     * @return
     */
    private Integer getDeposit(LeaseinfoPojo leaseinfoPojo) {
        //拿到id并在数据库查询商品租赁押金,拿到租赁押金
        Integer id = leaseinfoPojo.getId();
        RepertoryPojo repertoryPojo = repertoryMapper.selectById(id);
        Integer unitdeposit = repertoryPojo.getUnitdeposit();
        //拿到租赁的商品数量,并与租赁数量相乘得出总押金
        Integer count = leaseinfoPojo.getCount();
        return unitdeposit * count;
    }
}
