package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.LoginMapper;
import cn.doo.code.lease.entity.pojo.EmployeePojo;
import cn.doo.code.lease.service.LoginService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.FkEmailUtils;
import cn.doo.code.utils.redis.RedisUtil;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceimpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private RedisUtil jedis;

    @Autowired
    private FkEmailUtils emailUtils;


    /**
     * 登录
     *
     * @param username
     * @param password
     * @param code
     * @return
     */
    @Override
    public Map<String, Object> login(String username, String password, String code) {
        //数据库查询用户输入的账号密码是否存在于数据库
        EmployeePojo queryuser = loginMapper.queryUser(username, password);
        //如果查不到为空则返回前端错误信息
        if (queryuser == null) {
            return DooUtils.print(-1, "账号或密码错误", null, null);
        }

        //token
        String token = DooUtils.getToken(queryuser.getEmail());
        //存token到redis 有效期1小时
        jedis.setEx("token:" + username, token, 1, TimeUnit.HOURS);


        //从redis中拿取邮箱验证码  5分钟
        String verify = jedis.get("verify:" + username);
        System.out.println(verify);

        //值为空则缓存失效，反之成功
        if (StringUtils.equals(verify, code)) {
            if (code.equals(verify)) {
                HashMap<String, Object> stringObjectHashMap = new HashMap<>(3);
                stringObjectHashMap.put("token", token);
                stringObjectHashMap.put("employeename", queryuser.getUsername());
                stringObjectHashMap.put("employeeavatar", queryuser.getAvatar());
                return DooUtils.print(0, "登录成功", stringObjectHashMap, null);
            } else {
                return DooUtils.print(-1, "验证码失效或错误", null, null);
            }
        } else {
            return DooUtils.print(-1, "验证码失效或错误", null, null);
        }
    }

    /**
     * 发送验证码
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Map<String, Object> verify(String username, String password) throws Exception {
        //数据库查询用户输入的账号密码是否存在于数据库
        EmployeePojo queryuser = loginMapper.queryUser(username, password);
        if (queryuser == null) {
            return DooUtils.print(-1, "您输入的用户名或密码错误", null, null);
        }

        //--------生成用户注册发送的验证码-------------
        String codeV = DooUtils.makeCode(4);

        //将用户专属的token值写入redis中保存    有效时间：5分钟
        jedis.setEx("verify:" + queryuser.getUsername(), codeV, 5, TimeUnit.MINUTES);

        emailUtils.sendVerifyEmail(queryuser.getEmail(), queryuser.getEmail(), codeV);


        //完成用户校验并成功生成存储验证码，返回给前端
        return DooUtils.print(0, "验证码发送成功", null, null);
    }


}
