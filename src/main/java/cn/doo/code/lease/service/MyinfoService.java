package cn.doo.code.lease.service;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.EmployeePojo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 梦伴
 * @desc MyinfoService
 * @time 2021-06-02-10:18
 */
public interface MyinfoService {

    /**
     * 查询个人信息
     * @param tokenVerify
     * @return
     */
    Map<String, Object> queryUserInfo(TokenVerify tokenVerify);

    /**
     * 修改个人信息
     * @param tokenVerify
     * @param employeePojo
     * @return
     */
    Map<String, Object> updateInfo(TokenVerify tokenVerify, EmployeePojo employeePojo);

    /**
     * 修改头像
     * @param tokenVerify
     * @param id
     * @param file
     * @param request
     * @return
     */
    Map<String, Object> avatar(TokenVerify tokenVerify, Integer id, MultipartFile file, HttpServletRequest request);
}
