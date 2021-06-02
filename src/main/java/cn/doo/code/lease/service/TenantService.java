package cn.doo.code.lease.service;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.TenantPojo;

import java.util.Map;

public interface TenantService {

    /**
     * 查询所有租赁用户
     *
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     */
    Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit);

    /**
     * 新增一个租赁用户
     *
     * @param tokenVerify
     * @param tenantPojo
     * @return
     */
    Map<String, Object> insertOne(TokenVerify tokenVerify, TenantPojo tenantPojo);

    /**
     * 修改一个租赁用户
     *
     * @param tokenVerify
     * @param tenantPojo
     * @return
     */
    Map<String, Object> updateOne(TokenVerify tokenVerify, TenantPojo tenantPojo);

    /**
     * 删除一个租赁用户
     *
     * @param tokenVerify
     * @param id
     * @return
     */
    Map<String, Object> deleteOne(TokenVerify tokenVerify, String id);


}
