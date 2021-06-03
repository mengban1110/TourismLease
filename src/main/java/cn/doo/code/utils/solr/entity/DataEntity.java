package cn.doo.code.utils.solr.entity;

//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;

import cn.doo.code.lease.entity.pojo.LeasePojo;
import cn.doo.code.lease.entity.pojo.TenantPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.common.SolrDocument;

/**
 * @author 梦伴
 * @desc DataEntity
 * @time 2021-05-14-9:36
 */
@Data
public class DataEntity {
    //    @TableId(value = "gid", type = IdType.AUTO)
    @Field
    String id;
    @Field
    String leaseinfo;
    @Field
    String user;
    @Field
    String createtime;
    @Field
    String endtime;
    @Field
    int deposit;
    @Field
    int rent;
    @Field
    String breakinfo;
    @Field
    int status;

    public DataEntity() {
    }

    public DataEntity(SolrDocument result) {
        this.id = result.getFieldValue("id") + "";
        this.leaseinfo = result.getFieldValue("leaseinfo") + "";
        this.user = result.getFieldValue("user") + "";
        this.createtime = result.getFieldValue("createtime") + "";
        this.endtime = result.getFieldValue("endtime") + "";
        this.deposit = Integer.parseInt(result.getFieldValue("deposit") + "");
        this.rent = Integer.parseInt(result.getFieldValue("rent") + "");
        this.breakinfo = result.getFieldValue("breakinfo") + "";
        this.status = Integer.parseInt(result.getFieldValue("status") + "");
    }

    public DataEntity(SolrDocument result, String name) {
        this.id = result.getFieldValue("id") + "";
        this.leaseinfo = result.getFieldValue("leaseinfo") + "";
        this.user = name;
        this.createtime = result.getFieldValue("createtime") + "";
        this.endtime = result.getFieldValue("endtime") + "";
        this.deposit = Integer.parseInt(result.getFieldValue("deposit") + "");
        this.rent = Integer.parseInt(result.getFieldValue("rent") + "");
        this.breakinfo = result.getFieldValue("breakinfo") + "";
        this.status = Integer.parseInt(result.getFieldValue("status") + "");
    }

    public DataEntity(LeasePojo leasePojo, TenantPojo user) throws JsonProcessingException {
        this.id = leasePojo.getId() + "";
        this.leaseinfo = leasePojo.getLeaseinfo();
        
        ObjectMapper objectMapper = new ObjectMapper();
        this.user = objectMapper.writeValueAsString(user);

        this.createtime = leasePojo.getCreatetime()+"";
        this.endtime = leasePojo.getEndtime() + "";
        this.deposit = leasePojo.getDeposit();
        this.rent = leasePojo.getRent();
        this.breakinfo = leasePojo.getBreakinfo();
        this.status = leasePojo.getStatus();
    }
}
