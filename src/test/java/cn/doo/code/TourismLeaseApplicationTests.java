package cn.doo.code;

import cn.doo.code.lease.service.LeaseService;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootTest
//@RunWith(Parameterized.class)
@RunWith(Theories.class)
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

    @DataPoint //本意是数据点，在此的意思为将该数据标记为排列组合的方式传递值给测试案例中的参数
    public static String nameValue1 = "Tony";

    @DataPoint
    public static String nameValue2 = "Jim";

    @DataPoint
    public static int ageValue1 = 10;

    @DataPoint
    public static int ageValue2 = 20;

    @Theory //本意是推测，在此处的作用是以排列组合的方法给当前的测试用例传递参数
    public void testMethod(String name, int age) {
        System.out.println(String.format("%s's age is %s", name, age));
    }



}
