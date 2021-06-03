package cn.doo.code.lease.controller;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.service.HomePageService;
import cn.doo.code.utils.DooUtils;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RequestMapping("/api/homepage")
@CrossOrigin
@RestController
public class HomePageController {

    @Autowired
    private HomePageService homePageService;


    @RequestMapping(value = "/getinfo",method = RequestMethod.GET)
    public Map<String,Object> getinfo(TokenVerify tokenVerify){
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        return homePageService.getinfo(tokenVerify);
    }
}
