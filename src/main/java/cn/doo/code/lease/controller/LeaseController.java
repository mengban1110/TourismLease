package cn.doo.code.lease.controller;

import cn.doo.code.lease.entity.Leaseinfo;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.LeaseinfoPojo;
import cn.doo.code.lease.service.LeaseService;
import cn.doo.code.utils.DooUtils;
import com.alibaba.druid.util.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
     * @param tokenVerify
     * @param page
     * @param limit
     * @param phone
     * @return
     * @throws SolrServerException
     * @throws IOException
     * @desc 获取所有租赁商品信息
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
    public Map<String, Object> insertOne(TokenVerify tokenVerify, @RequestBody List<LeaseinfoPojo> leaseinfoPojo, Integer uid) throws Exception {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (leaseinfoPojo.size() == 0 || leaseinfoPojo == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        if (uid == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }


        return leaseService.insertOne(tokenVerify, leaseinfoPojo, uid);
    }

    @RequestMapping(value = "/updateOne")
    public Map<String, Object> updateOne(TokenVerify tokenVerify, @RequestBody List<Leaseinfo> leaseinfos, Integer id) throws Exception {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (leaseinfos.size() == 0 || leaseinfos == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        if (id == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return leaseService.updateOne(tokenVerify, leaseinfos, id);
    }

    @RequestMapping(value = "/deleteOne")
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, Integer id) throws Exception {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (id == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return leaseService.deleteOne(tokenVerify, id);
    }


    /**
     * 收据条
     *
     * @param tokenVerify
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download")
    public Map<String, Object> download(TokenVerify tokenVerify, Integer id, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (id == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return leaseService.download(tokenVerify, id, response);
    }


}
