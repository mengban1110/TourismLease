package cn.doo.code.lease.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("lease")
public class LeasePojo {
    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String leaseinfo;

    /**
     *
     */
    private Integer uid;

    /**
     *
     */
    private Date createtime;

    /**
     *
     */
    private Date endtime;

    /**
     *
     */
    private Integer deposit;

    /**
     *
     */
    private Integer rent;

    /**
     *
     */
    private String breakinfo;

    /**
     *
     */
    private Integer status;
}

