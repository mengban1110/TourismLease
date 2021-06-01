package cn.doo.code.lease.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("lease")
public class LeasePojo {
    /**
     *
     */
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

