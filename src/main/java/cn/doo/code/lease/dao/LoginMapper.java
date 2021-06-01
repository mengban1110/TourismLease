package cn.doo.code.lease.dao;


import cn.doo.code.lease.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface LoginMapper {

    /**
     * 查询用户
     *
     * @param username
     * @param password
     * @return
     */
    @Select("SELECT * FROM employee WHERE username=#{username} AND password=#{password}")
    Employee queryUser(@Param("username") String username, @Param("password") String password);

}
