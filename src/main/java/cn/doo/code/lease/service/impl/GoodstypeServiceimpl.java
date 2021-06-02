package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.GoodstypeMapper;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.GoodstypePojo;
import cn.doo.code.lease.service.GoodstypeService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.redis.RedisUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodstypeServiceimpl implements GoodstypeService {

    @Autowired
    GoodstypeMapper goodstypeMapper;
    @Autowired
    RedisUtil jedis;

    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库种类
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

        IPage<GoodstypePojo> pagez = new Page<>(page, limit);
        IPage<GoodstypePojo> goodstypePojoIPage = goodstypeMapper.selectPage(pagez, null);

        return DooUtils.print(0, "请求成功", goodstypePojoIPage.getRecords(), 123);
    }

    /**
     * @param tokenVerify
     * @param goodstypePojo
     * @return
     * @desc 新增一个种类
     */
    @Override
    public Map<String, Object> insertOne(TokenVerify tokenVerify, GoodstypePojo goodstypePojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify,jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        goodstypePojo.setCreatetime(new Date());

        goodstypeMapper.insert(goodstypePojo);
        return DooUtils.print(0,"添加成功",null,null);
    }

    /**
     * @param tokenVerify
     * @param goodstypePojo
     * @return
     * @desc 修改一个种类
     */
    @Override
    public Map<String, Object> updateOne(TokenVerify tokenVerify, GoodstypePojo goodstypePojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify,jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        goodstypeMapper.updateById(goodstypePojo);
        return DooUtils.print(0,"修改成功",null,null);
    }

    /**
     * @param tokenVerify
     * @param id
     * @return
     * @desc 删除一个种类
     */
    @Override
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, Integer id) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify,jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        goodstypeMapper.deleteById(id);
        return DooUtils.print(0,"删除成功",null,null);
    }
}
