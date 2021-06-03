package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.GoodstypeMapper;
import cn.doo.code.lease.dao.RepertoryMapper;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.GoodstypePojo;
import cn.doo.code.lease.entity.pojo.RepertoryPojo;
import cn.doo.code.lease.service.RepertoryService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class RepertoryServiceimpl implements RepertoryService {


    @Autowired
    GoodstypeMapper goodstypeMapper;
    @Autowired
    RepertoryMapper repertoryMapper;
    @Autowired
    RedisUtil jedis;

    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    @Override
    public Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }
        List<Map<String, Object>> list = repertoryMapper.queryAll(page, limit);
        return DooUtils.print(0, "请求成功", list, 5);
    }

    /**
     * @param tokenVerify
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    @Override
    public Map<String, Object> insertOne(TokenVerify tokenVerify, RepertoryPojo repertoryPojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }
        //从数据库查询type值是否存在于商品种类表  不存在则返回信息
        GoodstypePojo goodstypePojo = goodstypeMapper.selectById(repertoryPojo.getType());
        if (goodstypePojo == null) {
            return DooUtils.print(-3, "该商品种类不存在于数据库", null, null);
        }

        repertoryMapper.insert(repertoryPojo);

        return DooUtils.print(0, "新增成功", null, null);
    }

    /**
     * @param tokenVerify
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    @Override
    public Map<String, Object> updateOne(TokenVerify tokenVerify, RepertoryPojo repertoryPojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }
        //从数据库查询type值是否存在于商品种类表  不存在则返回信息
        GoodstypePojo goodstypePojo = goodstypeMapper.selectById(repertoryPojo.getType());
        if (goodstypePojo == null) {
            return DooUtils.print(-3, "该商品种类不存在于数据库", null, null);
        }
        //从数据库查询id值是否存在于商品表  不存在则返回信息
        RepertoryPojo repertoryPojo1 = repertoryMapper.selectById(repertoryPojo.getId());
        if (repertoryPojo1==null){
            return DooUtils.print(-3, "该商品不存在于数据库", null, null);
        }

        repertoryMapper.updateById(repertoryPojo);
        return DooUtils.print(0, "修改成功", null, null);
    }

    /**
     * @param tokenVerify
     * @param id
     * @return
     * @desc 删除一个仓库商品
     */
    @Override
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, Integer id) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }
        //从数据库查询id值是否存在于商品表  不存在则返回信息
        RepertoryPojo repertoryPojo1 = repertoryMapper.selectById(id);
        if (repertoryPojo1==null){
            return DooUtils.print(-3, "该商品不存在于数据库", null, null);
        }

        repertoryMapper.deleteById(id);
        return DooUtils.print(0, "删除成功", null, null);
    }


}
