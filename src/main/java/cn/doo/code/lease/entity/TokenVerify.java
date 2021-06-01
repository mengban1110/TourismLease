package cn.doo.code.lease.entity;

import lombok.Data;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-01-23:16
 */
@Data
public class TokenVerify {
    String username;
    String token;

    /**
     * 获取redisKey名字
     * @return
     */
    public String getRedisKey() {
        return "token:" + this.username;
    }
}
