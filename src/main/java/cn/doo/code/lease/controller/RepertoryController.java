package cn.doo.code.lease.controller;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.RepertoryPojo;
import cn.doo.code.lease.service.RepertoryService;
import cn.doo.code.utils.DooUtils;
import com.alibaba.druid.util.StringUtils;
import org.apache.ibatis.annotations.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/repertory")
public class RepertoryController {

    @Autowired
    private RepertoryService repertoryService;

    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }

        return repertoryService.queryAll(tokenVerify, page, limit);
    }

    /**
     * @param tokenVerify
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(TokenVerify tokenVerify, RepertoryPojo repertoryPojo) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (StringUtils.isEmpty(repertoryPojo.getName()) || repertoryPojo.getCount() == null || repertoryPojo.getMoney() == null || repertoryPojo.getUnitdeposit() == null || repertoryPojo.getUnitprice() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return repertoryService.insertOne(tokenVerify, repertoryPojo);
    }


    /**
     * @param tokenVerify
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public Map<String, Object> updateOne(TokenVerify tokenVerify, RepertoryPojo repertoryPojo) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (StringUtils.isEmpty(repertoryPojo.getName()) || repertoryPojo.getCount() == null || repertoryPojo.getMoney() == null || repertoryPojo.getUnitdeposit() == null || repertoryPojo.getUnitprice() == null || repertoryPojo.getId() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return repertoryService.updateOne(tokenVerify, repertoryPojo);
    }

    /**
     * @param tokenVerify
     * @param repertoryPojo
     * @return
     * @desc 删除一个仓库商品
     */
    @RequestMapping(value = "/deleteOne", method = RequestMethod.POST)
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, RepertoryPojo repertoryPojo) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }
        if (repertoryPojo.getId() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return repertoryService.deleteOne(tokenVerify, repertoryPojo.getId());
    }
}
