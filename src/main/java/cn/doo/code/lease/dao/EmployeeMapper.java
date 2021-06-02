package cn.doo.code.lease.dao;

import cn.doo.code.lease.entity.pojo.EmployeePojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<EmployeePojo> {
}
