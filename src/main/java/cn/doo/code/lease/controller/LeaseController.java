package cn.doo.code.lease.controller;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.LeaseinfoPojo;
import cn.doo.code.lease.service.LeaseService;
import cn.doo.code.utils.DooUtils;
import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/lease")
@CrossOrigin("*")
@RestController
public class LeaseController {

    @Autowired
    private LeaseService leaseService;


    /**
     * @desc 获取所有租赁商品信息
     * @param tokenVerify
     * @param page
     * @param limit
     * @param phone
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    @RequestMapping("/queryAll")
    public Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit, String phone) throws SolrServerException, IOException {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }

        return leaseService.queryAll(tokenVerify, phone, page, limit);
    }


    @RequestMapping(value = "/insertOne")
    public Map<String, Object> test(TokenVerify tokenVerify, @RequestBody List<LeaseinfoPojo> leaseinfoPojo, Integer uid) throws Exception {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (leaseinfoPojo.size()==0 || leaseinfoPojo==null){
            return DooUtils.print(-2,"参数异常",null,null);
        }

        if (uid == null) {
            return DooUtils.print(-2,"参数异常",null,null);
        }


        return leaseService.insertOne(tokenVerify,leaseinfoPojo,uid);
    }

}
