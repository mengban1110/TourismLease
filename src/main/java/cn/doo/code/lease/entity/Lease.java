package cn.doo.code.lease.entity;

import cn.doo.code.lease.entity.pojo.TenantPojo;
import lombok.Data;

import java.util.Date;

@Data
public class Lease {
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
    private TenantPojo tenant;

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

