package cn.doo.code.lease.service.impl;

import cn.doo.code.lease.dao.MyinfoMapper;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.EmployeePojo;
import cn.doo.code.lease.service.MyinfoService;
import cn.doo.code.utils.DooUtils;
import cn.doo.code.utils.redis.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-02-10:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MyinfoServiceimpl implements MyinfoService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MyinfoMapper myinfoMapper;

    /**
     * 查询个人信息
     *
     * @param tokenVerify
     * @return
     */
    @Override
    public Map<String, Object> queryUserInfo(TokenVerify tokenVerify) {

        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, redisUtil);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        //获取个人信息
        String username = tokenVerify.getUsername();
        QueryWrapper<EmployeePojo> employeePojoQueryWrapper = new QueryWrapper<>();
        employeePojoQueryWrapper.eq("name", username);
        EmployeePojo employeePojo = myinfoMapper.selectOne(employeePojoQueryWrapper);

        return DooUtils.print(0, "获取成功", employeePojo, null);
    }

    /**
     * 修改个人信息
     *
     * @param tokenVerify
     * @param employeePojo
     * @return
     */
    @Override
    public Map<String, Object> updateInfo(TokenVerify tokenVerify, EmployeePojo employeePojo) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, redisUtil);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }
        //获取个人信息
        String username = tokenVerify.getUsername();
        QueryWrapper<EmployeePojo> employeePojoQueryWrapper = new QueryWrapper<>();
        employeePojoQueryWrapper.eq("name", username);
        EmployeePojo employee = myinfoMapper.selectOne(employeePojoQueryWrapper);

        //设置个人信息
        employeePojo.setId(employee.getId());
        employeePojo.setName(employee.getName());
        if (employeePojo.getPassword().length() == 0) {
            employeePojo.setPassword(employee.getPassword());
        }

        myinfoMapper.updateById(employeePojo);
        return DooUtils.print(0, "修改成功", null, null);
    }

    /**
     * 修改头像
     *
     * @param tokenVerify
     * @param id
     * @param file
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> avatar(TokenVerify tokenVerify, Integer id, MultipartFile file, HttpServletRequest request) {
        /**
         * 对比token
         */
        Map<String, Object> verifyResultMap = DooUtils.getTokenVerifyResult(tokenVerify, redisUtil);
        if (verifyResultMap != null) {
            return verifyResultMap;
        }

        if (id == null) {
            //获取个人信息
            String username = tokenVerify.getUsername();
            QueryWrapper<EmployeePojo> employeePojoQueryWrapper = new QueryWrapper<>();
            employeePojoQueryWrapper.eq("name", username);
            EmployeePojo employee = myinfoMapper.selectOne(employeePojoQueryWrapper);
            id = employee.getId();
        }

        EmployeePojo employeePojo = myinfoMapper.selectById(id);

        /**
         * 获取图片名字 并 上传到服务器
         */
        String photoName = DooUtils.uploadPhotoAndReturnName(file, request);
        employeePojo.setAvatar(photoName);
        myinfoMapper.updateById(employeePojo);

        return DooUtils.print(0, "修改成功", null, null);
    }




}
