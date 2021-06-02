package cn.doo.code.lease.service;

import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.entity.pojo.GoodstypePojo;

import java.util.Map;

public interface GoodstypeService {
    /**
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库种类
     */
    Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit);

    /**
     * @desc 新增一个种类
     * @param tokenVerify
     * @param goodstypePojo
     * @return
     */
    Map<String, Object> insertOne(TokenVerify tokenVerify, GoodstypePojo goodstypePojo);

    /**
     * @desc 修改一个种类
     * @param tokenVerify
     * @param goodstypePojo
     * @return
     */
    Map<String, Object> updateOne(TokenVerify tokenVerify,GoodstypePojo goodstypePojo);

    /**
     * @desc 删除一个种类
     * @param tokenVerify
     * @param id
     * @return
     */
    Map<String,Object> deleteOne(TokenVerify tokenVerify,Integer id);
}
