package cn.doo.code.lease.dao;


import cn.doo.code.lease.entity.Lease;
import cn.doo.code.lease.entity.pojo.LeasePojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LeaseMapper extends BaseMapper<LeasePojo> {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "leaseinfo", property = "leaseinfo"),
            @Result(column = "createtime", property = "createtime"),
            @Result(column = "endtime", property = "endtime"),
            @Result(column = "deposit", property = "deposit"),
            @Result(column = "rent", property = "rent"),
            @Result(column = "breakinfo", property = "breakinfo"),
            @Result(column = "status", property = "status"),
            @Result(column = "uid", property = "user", one = @One(select = "cn.doo.code.lease.dao.TenantMapper.selectById"))
    })
    @Select("SELECT * FROM lease where id = #{id}")
    Lease queryOne(Integer id);

    @Update("UPDATE repertory SET count=count-#{count} WHERE id=#{id}")
    void reduceCount(Integer count, Integer id);


}
