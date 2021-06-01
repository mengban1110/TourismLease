package cn.doo.code.lease.dao;


import cn.doo.code.lease.entity.pojo.EmployeePojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


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
    EmployeePojo queryUser(@Param("username") String username, @Param("password") String password);

}
