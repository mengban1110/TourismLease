package cn.doo.code.lease.controller;


import cn.doo.code.lease.dao.EmployeeMapper;
import cn.doo.code.lease.entity.MapDoc;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.EmployeePojo;
import cn.doo.code.lease.service.EmployeeService;
import cn.doo.code.utils.DooUtils;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 人事管理接口
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {


    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;


    /**
     * 测试文档生成对象
     * @param test 测试对象
     * @return json
     */
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public MapDoc<EmployeePojo> testDoc(String test, int test2){
        MapDoc<EmployeePojo> mapDoc = new MapDoc();
        mapDoc.setCode(200);
        mapDoc.setCount(123);
        List<EmployeePojo> employeePojos = employeeMapper.selectList(null);
        mapDoc.setData(employeePojos);
        mapDoc.setMsg("请求成功");
        return mapDoc;
    }

    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有人事
     */
    @ResponseBody
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }

        return employeeService.queryAll(tokenVerify, page, limit);
    }

    /**
     * @param tokenVerify
     * @param employeePojo
     * @return
     * @desc 新增一个人事
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(TokenVerify tokenVerify, EmployeePojo employeePojo) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }
        if (employeePojo.getLevel() == null || StringUtils.isEmpty(employeePojo.getName()) || StringUtils.isEmpty(employeePojo.getEmail()) || StringUtils.isEmpty(employeePojo.getPassword())) {
            return DooUtils.print(-2, "参数异常", null, null);
        }


        return employeeService.insertOne(tokenVerify, employeePojo);
    }

    /**
     * @param tokenVerify
     * @param employeePojo
     * @return
     * @desc 修改一个人事信息
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public Map<String, Object> updateOne(TokenVerify tokenVerify, EmployeePojo employeePojo) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }
        if (employeePojo.getId() == null || employeePojo.getLevel() == null || StringUtils.isEmpty(employeePojo.getName()) || StringUtils.isEmpty(employeePojo.getEmail()) || StringUtils.isEmpty(employeePojo.getPassword())) {
            return DooUtils.print(-2, "参数异常", null, null);
        }
        return employeeService.updateOne(tokenVerify, employeePojo);
    }


    /**
     * @param tokenVerify
     * @param employeePojo
     * @return
     * @desc 删除一个人事信息
     */
    @RequestMapping(value = "deleteOne", method = RequestMethod.POST)
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, EmployeePojo employeePojo) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }
        if (employeePojo.getId() == null) {
            return DooUtils.print(-2, "未登录", null, null);
        }
        return employeeService.deleteOne(tokenVerify, employeePojo.getId());
    }


}
