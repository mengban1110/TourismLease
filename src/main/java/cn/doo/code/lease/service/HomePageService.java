package cn.doo.code.lease.service;

import cn.doo.code.lease.entity.TokenVerify;

import java.util.Map;

public interface HomePageService {


    Map<String,Object> getinfo(TokenVerify tokenVerify);
}
