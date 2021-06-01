package cn.doo.code;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class TourismLeaseApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Test
    void contextLoads() {
        System.out.println("redisTemplate = " + redisTemplate);
        String token = redisTemplate.opsForValue().get("token");
        System.out.println("token = " + token);
    }

}
