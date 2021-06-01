package cn.doo.code.lease.controller;

import cn.doo.code.lease.entity.Employee;
import cn.doo.code.lease.service.LoginService;
import cn.doo.code.lease.service.impl.LoginServiceimpl;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.EmailUtils;
import cn.doo.code.utils.redis.RedisUtil;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/api/login")
@CrossOrigin
@RestController
public class LoginController {




    @Autowired
    private LoginService loginService;


    /**
     * 发送一个有效时间1h的验证码
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public Map<String, Object> verify(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return DooUtils.print(-1, "用户名或密码错误", null, null);
        }

        return loginService.verify(username,password);
    }


    /**
     * 登录方法
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(String username, String password, String code) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return DooUtils.print(-1, "用户名或密码错误", null, null);
        }
        if (StringUtils.isEmpty(code)) {
            return DooUtils.print(-1, "验证码错误", null, null);
        }
        System.out.println("进入");

        return loginService.login(username,password,code);
    }


}