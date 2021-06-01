package cn.doo.code.lease.service;


import java.util.Map;

public interface LoginService {

     /**
      * 登录
      * @param username
      * @param password
      * @param code
      * @return
      */
     Map<String, Object> login(String username, String password, String code);

     /**
      * 发送验证码
      * @param username
      * @param password
      * @return
      */
     Map<String, Object> verify(String username, String password);

}
