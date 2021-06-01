package cn.doo.code.lease.service;


import java.util.Map;

public interface LoginService {

     Map<String, Object> login(String username, String password, String code);

     Map<String, Object> verify(String username, String password);

}
