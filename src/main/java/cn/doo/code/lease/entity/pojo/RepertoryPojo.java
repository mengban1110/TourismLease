package cn.doo.code.lease.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repertory")
public class RepertoryPojo {
    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
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
    private Integer unitdeposit;

    /**
     *
     */
    private Integer type;
}

