package cn.doo.code.lease.controller;

import cn.doo.code.lease.entity.Employee;
import cn.doo.code.lease.service.impl.EmployeeService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.EmailUtils;
import cn.doo.code.utils.VcodeUtils;
import cn.doo.code.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
public class EmployeeController {


    @Autowired
    private RedisUtil jedis;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 发送一个有效时间1h的验证码
     */
    @RequestMapping(value = "/vcode", method = RequestMethod.POST)
    public Map<String, Object> vcode(String username,String password) {

        //数据库查询用户输入的账号密码是否存在于数据库
        Employee queryuser = employeeService.queryUser(username, password);
        if (queryuser==null){
            return DooUtils.print(-1,"您输入的用户名或密码错误",null,null);
        }

        //--------生成用户注册发送的验证码-------------
        String  codeV= VcodeUtils.vcode();

        //将用户专属的token值写入redis中保存    有效时间：1小时
        jedis.setEx(queryuser.getUsername()+"token", codeV,1, TimeUnit.HOURS);


        EmailUtils.sendSimpleMail("421028879@qq.com","验证码","您的验证码为"+codeV);

        //完成用户校验并成功生成存储验证码，返回给前端
        return DooUtils.print(0,"获取成功",codeV,null);
    }



    /**
     * 登录方法
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(String username,String password,String vcode) {
        //json
        Map<String, Object> json = new HashMap();

        System.out.println(username);
        System.out.println(password);

        Employee queryuser = employeeService.queryUser(username, password);
        System.out.println(queryuser);

        if (queryuser==null){
            json.put("code", -1);
            json.put("msg", "获取成功");
            json.put("data", "您输入的用户名或密码错误，请重新输入");

            return json;
        }


        //从redis中拿取token值
        String tokenMessage = jedis.get("token");
        //值为空则缓存失效，反之成功
         if (tokenMessage!=null)
         {
            System.out.println("缓存没有失效，用户可以注册。。。。");
            if (vcode.equals(tokenMessage))
            {
                json.put("code", 0);
                json.put("msg", "获取成功");
                json.put("data", "您输入的手机验证码正确，恭喜登录成功");
            }else{
                json.put("code", 1);
                json.put("msg", "获取成功");
                json.put("data", "您输入的手机验证码不正确，请重新获取验证码并重新登录");
            }
        }
        else
        {
            json.put("code", 2);
            json.put("msg", "获取成功");
            json.put("data", "缓存已经失效，本次输入验证码失败，请重新获取验证码");
        }
        return json;
    }







}
