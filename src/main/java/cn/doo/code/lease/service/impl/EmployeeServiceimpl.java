package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.EmployeeMapper;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.EmployeePojo;
import cn.doo.code.lease.service.EmployeeService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.redis.RedisUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceimpl implements EmployeeService {


    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RedisUtil jedis;


    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有人事
     */
    @Override
    public Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        IPage<EmployeePojo> pagez = new Page<>(page, limit);
        IPage<EmployeePojo> employeePojoIPage = employeeMapper.selectPage(pagez, null);

        return DooUtils.print(0, "请求成功", employeePojoIPage.getRecords(), null);
    }

    /**
     * @desc 新增一个人事
     * @param tokenVerify
     * @param employeePojo
     * @return
     */
    @Override
    public Map<String, Object> insertOne(TokenVerify tokenVerify, EmployeePojo employeePojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        employeeMapper.insert(employeePojo);

        return DooUtils.print(0,"新增成功",null,null);
    }

    /**
     * @param tokenVerify
     * @param employeePojo
     * @return
     * @desc 修改一个人事
     */
    @Override
    public Map<String, Object> updateOne(TokenVerify tokenVerify, EmployeePojo employeePojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify,jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        employeeMapper.updateById(employeePojo);

        return DooUtils.print(0,"修改成功",null,null);
    }

    /**
     * @param tokenVerify
     * @param id
     * @return
     * @desc 删除一个人事
     */
    @Override
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, Integer id) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify,jedis);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }


        employeeMapper.deleteById(id);

        return DooUtils.print(0,"删除成功",null,null);
    }
}
