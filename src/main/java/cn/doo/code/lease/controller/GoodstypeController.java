package cn.doo.code.lease.controller;


import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.GoodstypePojo;
import cn.doo.code.lease.service.GoodstypeService;
import cn.doo.code.utils.DooUtils;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/goodstype")
public class GoodstypeController {

    @Autowired
    private GoodstypeService goodstypeService;


    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库的种类
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

        return goodstypeService.queryAll(tokenVerify, page, limit);
    }

    /**
     * @param tokenVerify
     * @param goodstypePojo
     * @return
     * @desc 新增一个种类
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(TokenVerify tokenVerify, GoodstypePojo goodstypePojo) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (StringUtils.isEmpty(goodstypePojo.getName())) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return goodstypeService.insertOne(tokenVerify, goodstypePojo);
    }

    /**
     * @desc 修改一个种类
     * @param tokenVerify
     * @param goodstypePojo
     * @return
     */
    @RequestMapping(value = "/updateOne",method = RequestMethod.POST)
    public Map<String,Object> updateOne(TokenVerify tokenVerify,GoodstypePojo goodstypePojo){
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }
        if (StringUtils.isEmpty(goodstypePojo.getName()) || goodstypePojo.getId() == null){
            return  DooUtils.print(-2,"参数异常",null,null);
        }

        return goodstypeService.updateOne(tokenVerify,goodstypePojo);
    }

    /**
     * @desc 删除一个种类
     * @param tokenVerify
     * @param goodstypePojo
     * @return
     */
    @RequestMapping(value = "/deleteOne",method = RequestMethod.POST)
    public Map<String,Object> deleteOne(TokenVerify tokenVerify,GoodstypePojo goodstypePojo){
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }
        if (goodstypePojo.getId()==null){
            return DooUtils.print(-2,"参数异常",null,null);
        }
        return goodstypeService.deleteOne(tokenVerify,goodstypePojo.getId());
    }

}
