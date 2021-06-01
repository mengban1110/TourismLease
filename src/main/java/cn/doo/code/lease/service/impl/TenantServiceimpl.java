package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.TenantMapper;
import cn.doo.code.lease.entity.pojo.TenantPojo;
import cn.doo.code.lease.service.TenantService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.redis.RedisUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TenantServiceimpl implements TenantService {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private RedisUtil jedis;

    /**
     * @param token
     * @param page
     * @param limit
     * @return
     * @desc 查询所有租赁用户
     */
    @Override
    public Map<String, Object> queryAll(String token, Integer page, Integer limit) {

        //从redis查询token是否过期
        String token2 = jedis.get("token");
        if (StringUtils.isEmpty(token2)){
            return DooUtils.print(-1,"未登录",null,null);
        }


        Page<TenantPojo> page2 = new Page<>(page,limit);
        IPage<TenantPojo> iPage=tenantMapper.selectPage(page2,null);
        return DooUtils.print(0,"请求成功",iPage,123);
    }

    /**
     * @param token
     * @param name
     * @param phone
     * @desc 新增一个租赁用户
     */
    @Override
    public Map<String, Object> insertOne(String token, String name, String phone) {
        //从redis查询token是否过期
        String token2 = jedis.get("token");
        if (StringUtils.isEmpty(token2)){
            return DooUtils.print(-1,"未登录",null,null);
        }

        TenantPojo tenantPojo = new TenantPojo();
        tenantPojo.setName(name);
        tenantPojo.setPhone(phone);

        tenantMapper.insert(tenantPojo);

        return DooUtils.print(0,"新增成功",null,null);
    }

    /**
     * @param token
     * @param id
     * @return
     * @desc 删除一个租赁用户
     */
    @Override
    public Map<String, Object> deleteOne(String token, String id) {
        //从redis查询token是否过期
        String token2 = jedis.get("token");
        if (StringUtils.isEmpty(token2)){
            return DooUtils.print(-1,"未登录",null,null);
        }

        tenantMapper.deleteById(id);
        return DooUtils.print(0,"删除成功",null,null);
    }
}
