package cn.doo.code.lease.dao;


import cn.doo.code.lease.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository("EmployeeMapper")
public interface EmployeeMapper {

    @Select("SELECT * FROM employee WHERE username=#{username} AND password=#{password}")
    Employee queryUser(@Param("username") String username, @Param("password") String password);

}
