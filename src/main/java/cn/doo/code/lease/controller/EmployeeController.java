package cn.doo.code.lease.controller;


import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.EmployeePojo;
import cn.doo.code.lease.entity.pojo.LeaseinfoPojo;
import cn.doo.code.lease.service.EmployeeService;
import cn.doo.code.utils.DooUtils;
import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有人事
     */
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


    @RequestMapping(value = "test")
    public void test(@RequestBody List<LeaseinfoPojo> LeaseinfoPojo) throws JSONException {
        System.out.println(LeaseinfoPojo);
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        JSONArray jsonArray = jsonObject.getJSONArray("beans");
//        List<LeaseinfoPojo> list = JSONArray.toCollection(jsonArray, List.class);
//        System.out.println(list);
    }


}
