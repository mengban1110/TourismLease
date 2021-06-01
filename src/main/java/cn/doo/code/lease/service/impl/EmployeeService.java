package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.EmployeeMapper;
import cn.doo.code.lease.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("EmployeeService")
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * @desc 在数据库查询用户名密码是否正确
     * @param username
     * @param password
     * @return
     */
    public Employee queryUser(String username, String password) {

        return employeeMapper.queryUser(username,password);
    }








}
