package cn.doo.code.lease.entity;

import cn.doo.code.lease.entity.pojo.TenantPojo;
import lombok.Data;

import java.util.Date;

@Data
public class Lease {
    /**
     * id
     */
    private Integer id;

    /**
     * leaseinfo
     */
    private String leaseinfo;

    /**
     * user
     */
    private TenantPojo user;

    /**
     * createtime
     */
    private Date createtime;

    /**
     * endtime
     */
    private Date endtime;

    /**
     * deposit
     */
    private Integer deposit;

    /**
     * rent
     */
    private Integer rent;

    /**
     * breakinfo
     */
    private String breakinfo;

    /**
     * status
     */
    private Integer status;
}

