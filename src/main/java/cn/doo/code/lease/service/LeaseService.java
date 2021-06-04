package cn.doo.code.lease.service;

import cn.doo.code.lease.entity.Leaseinfo;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.LeaseinfoPojo;
import org.apache.solr.client.solrj.SolrServerException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface LeaseService {

    /**
     * 获取所有租赁订单信息
     *
     * @param tokenVerify
     * @param phone
     * @param page
     * @param limit
     * @return
     */
    Map<String, Object> queryAll(TokenVerify tokenVerify, String phone, Integer page, Integer limit) throws SolrServerException, IOException;


    /**
     * @param tokenVerify
     * @param leaseinfo
     * @param uid
     * @return
     * @desc 新增一个订单信息
     */
    Map<String, Object> insertOne(TokenVerify tokenVerify, List<LeaseinfoPojo> leaseinfo, Integer uid) throws Exception;

    /**
     * 结束租赁
     *
     * @param tokenVerify
     * @param leaseinfo
     * @return
     * @throws Exception
     */
    Map<String, Object> updateOne(TokenVerify tokenVerify, List<Leaseinfo> leaseinfo, Integer id) throws Exception;

    /**
     * 删除订单
     * @param tokenVerify
     * @param id
     * @return
     */
    Map<String, Object> deleteOne(TokenVerify tokenVerify, Integer id);

    /**
     * 下载收据
     * @param tokenVerify
     * @param id
     * @return
     */
    Map<String, Object> download(TokenVerify tokenVerify, Integer id, HttpServletResponse response) throws IOException;
}
