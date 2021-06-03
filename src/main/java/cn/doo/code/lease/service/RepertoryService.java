package cn.doo.code.lease.service;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.RepertoryPojo;

import java.util.Map;

public interface RepertoryService {

    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit);

    /**
     * @param tokenVerify
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    Map<String, Object> insertOne(TokenVerify tokenVerify, RepertoryPojo repertoryPojo);

    /**
     * @param tokenVerify
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    Map<String, Object> updateOne(TokenVerify tokenVerify, RepertoryPojo repertoryPojo);

    /**
     * @param tokenVerify
     * @param id
     * @return
     * @desc 删除一个仓库商品
     */
    Map<String, Object> deleteOne(TokenVerify tokenVerify, Integer id);
}
