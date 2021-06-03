package cn.doo.code;

import cn.doo.code.lease.dao.RepertoryMapper;
import cn.doo.code.lease.entity.Leaseinfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class TourismLeaseApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RepertoryMapper repertoryMapper;

    @Test
    void contextLoads() throws JsonProcessingException {
        //查询 返回json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[{\"id\":1,\"number\":[\"0017edcf7\",\"0017e511\"]},{\"id\":2,\"number\":[\"0028e2ab\"]},{\"id\":3,\"number\":[\"0037edcf7\",\"0037e511\"]},{\"id\":4,\"number\":[\"0047edcf7\",\"0047e511\",\"0047edcf7\",\"0047e511\"]},{\"id\":5,\"number\":[\"0057edcf7\", \"0057e511\"]}]";
        List<Leaseinfo> list = objectMapper.readValue(json, List.class);
        System.out.println(list);


        String asd = objectMapper.writeValueAsString(list);
        System.out.println(asd);
//        System.out.println(leaseinfo);
    }


}
