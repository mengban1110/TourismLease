package cn.doo.code;

import cn.doo.code.lease.dao.LeaseMapper;
import cn.doo.code.lease.dao.RepertoryMapper;
import cn.doo.code.lease.entity.Leaseinfo;
import cn.doo.code.lease.entity.TokenVerify;
import cn.doo.code.lease.service.LeaseService;
import cn.doo.code.utils.solr.SolrUtil;
import cn.doo.code.utils.solr.entity.DataEntity;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class TourismLeaseApplicationTests {

//    @Autowired
//    private StringRedisTemplate redisTemplate;
//    @Autowired
//    private RepertoryMapper repertoryMapper;
//    @Autowired
//    private LeaseMapper leaseMapper;


//    @Autowired
//    private SolrUtil SolrUtil;

    @Autowired
    private LeaseService leaseService;


    @Test
    void contextLoads() throws IOException, SQLException, SolrServerException, ClassNotFoundException {


        String uuid = IdUtil.simpleUUID();

        System.out.println(StrUtil.sub(uuid,0,6));


//        TokenVerify tokenVerify = new TokenVerify();
//        tokenVerify.setToken("1312313131");
//        tokenVerify.setUsername("mengban");
//        Map<String, Object> map = leaseService.queryAll(tokenVerify, "156", 1, 1);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String s = objectMapper.writeValueAsString(map);
//        System.out.println("s = " + s);
//        List<DataEntity> dataEntities2 = SolrUtil.queryIndexItems(1, 999, "1561888");
//        dataEntities2.forEach(System.out::println);

//        //查询 返回json
//        /*ObjectMapper objectMapper = new ObjectMapper();
//        String json = "[{\"id\":1,\"number\":[\"0017edcf7\",\"0017e511\"]},{\"id\":2,\"number\":[\"0028e2ab\"]},{\"id\":3,\"number\":[\"0037edcf7\",\"0037e511\"]},{\"id\":4,\"number\":[\"0047edcf7\",\"0047e511\",\"0047edcf7\",\"0047e511\"]},{\"id\":5,\"number\":[\"0057edcf7\", \"0057e511\"]}]";
//        List<Leaseinfo> list = objectMapper.readValue(json, List.class);
//        System.out.println(list);
//
//
//        String asd = objectMapper.writeValueAsString(list);
//        System.out.println(asd);*/
////        System.out.println(leaseinfo);
//
//        List<Map<String, Object>> list = leaseMapper.queryAll(0, 999);
//        for (Map<String, Object> list2:list) {
//            System.out.println(list2);
//        }


//        SolrUtil.buildIndex();

//        List<DataEntity> dataEntities = SolrUtil.queryIndexItems(null, 1, 0);
//        dataEntities.forEach(System.out::println);
//
//
//        System.out.println("===========================");
//        System.out.println("===========================");
//        System.out.println("===========================");
//        System.out.println("===========================");


    }


}
