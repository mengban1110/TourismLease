package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.HomePageMapper;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.service.HomePageService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class HomePageServiceimpl implements HomePageService {


    @Autowired
    HomePageMapper homePageMapper;
    @Autowired
    RedisUtil jedis;


    @Override
    public Map<String, Object> getinfo(TokenVerify tokenVerify) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }
        //现在总订单(包括未完成和已完成)
        Integer LeaseCount = homePageMapper.queryLeaseCount();
        //租赁用户数量
        Integer tenantCount = homePageMapper.querytenantCount();
        //库存内现可租的总数量
        Integer RepertoryCount = homePageMapper.queryRepertoryCount();
        //库存内种类数量
        Integer goodstypeCount = homePageMapper.queryGoodstypeCount();
        //纯利润
        Integer profitCount = homePageMapper.queryProfitCount();
        //Echarts图
        List<Map<String, Object>> echarts = homePageMapper.queryechartsCount();

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> preview = new HashMap<>();

        preview.put("lease", LeaseCount);
        preview.put("tenant", tenantCount);
        preview.put("repertory", RepertoryCount);
        preview.put("repertorytype", goodstypeCount);
        preview.put("profit", profitCount);

        data.put("preview", preview);
        data.put("echarts", echarts);


        return DooUtils.print(0, "请求成功", data, null);
    }
}
