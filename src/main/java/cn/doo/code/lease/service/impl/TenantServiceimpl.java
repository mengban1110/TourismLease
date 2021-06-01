package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.TenantMapper;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.TenantPojo;
import cn.doo.code.lease.service.TenantService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.redis.RedisUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TenantServiceimpl implements TenantService {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private RedisUtil jedis;

    /**
     * @param page
     * @param limit
     * @return
     * @desc 查询所有租赁用户
     */
    @Override
    public Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit) {

        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = getTokenVerifyResult(tokenVerify);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        /**
         * 查询所有
         */
        IPage<TenantPojo> pagez = new Page<>(page, limit);
        IPage<TenantPojo> tenantPojoIPage = tenantMapper.selectPage(pagez, null);

        return DooUtils.print(0,"请求成功",tenantPojoIPage.getRecords(),tenantPojoIPage.getTotal());
    }


    /**
     * 新增一个租赁用户
     * @param tokenVerify
     * @param tenantPojo
     * @return
     */
    @Override
    public Map<String, Object> insertOne(TokenVerify tokenVerify,TenantPojo tenantPojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = getTokenVerifyResult(tokenVerify);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        /**
         * 添加租户信息
         */
        tenantMapper.insert(tenantPojo);

        return DooUtils.print(0,"新增成功",null,null);
    }

    /**
     * 修改一个租赁用户
     *
     * @param tokenVerify
     * @param tenantPojo
     * @return
     */
    @Override
    public Map<String, Object> updateOne(TokenVerify tokenVerify, TenantPojo tenantPojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = getTokenVerifyResult(tokenVerify);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        /**
         * 修改租户信息
         */
        tenantMapper.updateById(tenantPojo);

        return DooUtils.print(0,"修改成功",null,null);

    }

    /**
     * @param id
     * @return
     * @desc 删除一个租赁用户
     */
    @Override
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, String id) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = getTokenVerifyResult(tokenVerify);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        tenantMapper.deleteById(id);
        return DooUtils.print(0,"删除成功",null,null);
    }



    /**
     * 获取token校验
     * @param tokenVerify
     * @return
     */
    private Map<String, Object> getTokenVerifyResult(TokenVerify tokenVerify) {
        Boolean hasKey = jedis.hasKey(tokenVerify.getRedisKey());
        //token过期了
        if (!hasKey) {
            return DooUtils.print(-1,"未登录",null,null);
        }

        //获取token 进行对比是否相同
        String token = jedis.get(tokenVerify.getRedisKey());
        boolean result = StringUtils.equals(token, tokenVerify.getToken());
        if (!result) {
            return DooUtils.print(-1,"未登录",null,null);
        }
        return null;
    }
}
