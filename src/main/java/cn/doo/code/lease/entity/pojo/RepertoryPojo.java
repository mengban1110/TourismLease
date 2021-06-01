package cn.doo.code.lease.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repertory")
public class RepertoryPojo {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer count;

    /**
     *
     */
    private Integer money;

    /**
     *
     */
    private Integer unitprice;

    /**
     *
     */
    private Integer type;
}

