package cn.doo.code.lease.dao;

import cn.doo.code.lease.entity.pojo.LeasePojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface HomePageMapper extends BaseMapper{

    /**
     * @DESC 总订单的数量(包括未完成和已完成)
     * @return
     */
    @Select("select count(1) from lease")
    Integer queryLeaseCount();

    /**
     * @desc 租赁用户数量
     * @return
     */
    @Select("select count(1) from tenant")
    Integer querytenantCount();

    /**
     * @desc 库存内现可租的总数量
     * @return
     */
    @Select("SELECT SUM(count) FROM repertory")
    Integer queryRepertoryCount();

    /**
     * @desc 库存内种类数量
     * @return
     */
    @Select("select count(1) from goodstype")
    Integer queryGoodstypeCount();

    /**
     * @desc 纯利润
     * @return
     */
    @Select("SELECT SUM(rent) FROM lease")
    Integer queryProfitCount();

    /**
     * @desc Echarts图
     * @return
     */
    @Select("SELECT goodstype.`name`,count(1) AS 'value' FROM repertory LEFT JOIN goodstype ON repertory.type=goodstype.id Group By goodstype.`name`")
    List<Map<String,Object>> queryechartsCount();

}
