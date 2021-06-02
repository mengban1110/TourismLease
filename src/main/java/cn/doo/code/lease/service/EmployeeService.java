package cn.doo.code.lease.service;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.EmployeePojo;

import java.io.File;
import java.util.Map;

public interface EmployeeService {
    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有人事
     */
    Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit);

    /**
     * @param tokenVerify
     * @param employeePojo
     * @return
     * @desc 新增一个人事
     */
    Map<String, Object> insertOne(TokenVerify tokenVerify, EmployeePojo employeePojo);

    /**
     * @param tokenVerify
     * @param employeePojo
     * @return
     * @desc 修改一个人事
     */
    Map<String, Object> updateOne(TokenVerify tokenVerify, EmployeePojo employeePojo);

    /**
     * @desc 删除一个人事
     * @param tokenVerify
     * @param id
     * @return
     */
    Map<String,Object> deleteOne(TokenVerify tokenVerify,Integer id);
}
