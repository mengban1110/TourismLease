package cn.doo.code.lease.dao;

import cn.doo.code.lease.entity.Repertory;
import cn.doo.code.lease.entity.pojo.RepertoryPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RepertoryMapper extends BaseMapper<RepertoryPojo> {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "count", property = "count"),
            @Result(column = "money", property = "money"),
            @Result(column = "unitdeposit", property = "unitdeposit"),
            @Result(column = "unitprice", property = "unitprice"),
            @Result(column = "type", property = "type", one = @One(select = "cn.doo.code.lease.dao.GoodstypeMapper.selectById"))
    })
    @Select("SELECT * FROM repertory limit #{page},#{limit}")
    List<Repertory> queryAll(@Param("page") Integer page, @Param("limit") Integer limit);

}
