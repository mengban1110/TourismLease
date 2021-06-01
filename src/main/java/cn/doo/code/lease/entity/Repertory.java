package cn.doo.code.lease.entity;

import cn.doo.code.lease.entity.pojo.GoodstypePojo;
import lombok.Data;

@Data
public class Repertory {
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
    private GoodstypePojo goodstype;

}

