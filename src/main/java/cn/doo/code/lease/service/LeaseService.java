package cn.doo.code.lease.service;

import cn.doo.code.lease.entity.Leaseinfo;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.LeaseinfoPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface LeaseService {

    /**
     * 获取所有租赁订单信息
     * @param tokenVerify
     * @param phone
     * @param page
     * @param limit
     * @return
     */
    Map<String,Object> queryAll(TokenVerify tokenVerify, String phone, Integer page, Integer limit) throws SolrServerException, IOException;


    /**
     * @desc 新增一个订单信息
     * @param tokenVerify
     * @param leaseinfo
     * @param uid
     * @return
     */
    Map<String,Object> insertOne(TokenVerify tokenVerify, List<LeaseinfoPojo> leaseinfo, Integer uid) throws Exception;
}
