package cn.doo.code;

import cn.doo.code.lease.service.LeaseService;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootTest
class TourismLeaseApplicationTests {

    @Autowired
    private LeaseService leaseService;


    @Test
    void contextLoads() throws IOException, SQLException, SolrServerException, ClassNotFoundException {
//
//        TokenVerify tokenVerify = new TokenVerify();
//        tokenVerify.setToken("admin");
//        tokenVerify.setUsername("mengban");
//        leaseService.download(tokenVerify,1,response)
    }


}
