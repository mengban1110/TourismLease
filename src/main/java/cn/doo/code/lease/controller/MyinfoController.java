package cn.doo.code.lease.controller;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.EmployeePojo;
import cn.doo.code.lease.service.MyinfoService;
import cn.doo.code.utils.DooUtils;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-02-10:15
 */
@CrossOrigin
@RestController
@RequestMapping("/api/myinfo")
public class MyinfoController {

    @Autowired
    private MyinfoService myinfoService;

    /**
     * 获取登录用户信息
     *
     * @param tokenVerify
     * @return
     */
    @RequestMapping(value = "/getinfo", method = RequestMethod.GET)
    public Map<String, Object> getinfo(TokenVerify tokenVerify) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }
        return myinfoService.queryUserInfo(tokenVerify);
    }

    /**
     * 修改登录用户信息
     *
     * @param tokenVerify
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> update(TokenVerify tokenVerify, EmployeePojo employeePojo) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (StringUtils.isEmpty(employeePojo.getEmail()) || employeePojo.getLevel() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return myinfoService.updateInfo(tokenVerify, employeePojo);
    }


    /**
     * 以id修改用户头像
     *
     * @param tokenVerify
     * @return
     */
    @RequestMapping("/avatar")
    public Map<String, Object> update(TokenVerify tokenVerify, Integer id, MultipartFile file, HttpServletRequest request) {
        if (StringUtils.isEmpty(tokenVerify.getToken()) || StringUtils.isEmpty(tokenVerify.getUsername())) {
            return DooUtils.print(-1, "未登录", null, null);
        }

        if (file == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return myinfoService.avatar(tokenVerify, id, file, request);
    }


    /***
     * 下载图片
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/img/{fileName:.+}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) throws IOException {
        if (StringUtils.isEmpty(fileName)) {
            DooUtils.print(-2, "参数异常", null, null);
        }
        DooUtils.downloadPhoto(request, response, fileName);
    }


}
