package cn.doo.code.lease.dao;

import cn.doo.code.lease.entity.pojo.GoodstypePojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodstypeMapper extends BaseMapper<GoodstypePojo> {
}
