package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.service.HomePageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class HomePageServiceimpl implements HomePageService {
    @Override
    public Map<String, Object> getinfo(TokenVerify tokenVerify) {
        return null;
    }
}
