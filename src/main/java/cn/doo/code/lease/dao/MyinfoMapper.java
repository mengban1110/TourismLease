package cn.doo.code.lease.dao;

import cn.doo.code.lease.entity.pojo.EmployeePojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-02-10:17
 */
@Repository
@Mapper
public interface MyinfoMapper extends BaseMapper<EmployeePojo> {

    /**
     * 查询用户
     *
     * @param username
     * @param password
     * @return
     */
    @Select("SELECT * FROM employee WHERE name=#{username} AND password=#{password}")
    EmployeePojo queryUser(@Param("username") String username, @Param("password") String password);

}
