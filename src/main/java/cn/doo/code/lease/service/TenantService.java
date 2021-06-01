package cn.doo.code.lease.service;

import java.util.Map;

public interface TenantService {

    /**
     * @param token
     * @param page
     * @param limit
     * @return
     * @desc 查询所有租赁用户
     */
    Map<String, Object> queryAll(String token, Integer page, Integer limit);

    /**
     * @param token
     * @param name
     * @param phone
     * @desc 新增一个租赁用户
     */
    Map<String, Object> insertOne(String token, String name, String phone);

    /**
     * @param token
     * @param id
     * @return
     * @desc 删除一个租赁用户
     */
    Map<String, Object> deleteOne(String token, String id);



}
