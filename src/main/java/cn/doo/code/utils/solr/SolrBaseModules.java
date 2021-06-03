package cn.doo.code.utils.solr;

import cn.doo.code.utils.solr.entity.DataEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 梦伴
 * @desc Solr连接
 * @time 2021-05-06-14:51
 */
@Component
public class SolrBaseModules {
    public static SolrClient client;
    private static String url;

    static {
        url = "http://localhost:8983/solr/tourismlease";
        client = new Builder(url).build();
    }


    /**
     * 查询所有
     *
     * @param keywords 关键词
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public static List<DataEntity> queryAllIndex(String keywords, int startOfPage, int numberOfPage) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setStart(startOfPage);
        query.setRows(numberOfPage);
        query.setQuery(keywords);
        QueryResponse queryResponse = client.query(query);
        //获取结果集
        SolrDocumentList results = queryResponse.getResults();
        //获取查询到的个数
        String numFound = String.valueOf(results.getNumFound());
        //打印查询结果
        printResults(results, numFound, keywords);

        //开始返回对象
        List<DataEntity> arr = new ArrayList<>();
        //封装对象
        for (SolrDocument result : results) {
            DataEntity dataEntity = new DataEntity(result);
            arr.add(dataEntity);
        }

        return arr;
    }

    /**
     * 高亮查询
     *
     * @param keywords     关键词
     * @param startOfPage  开始页
     * @param numberOfPage 每页查询个数
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public static List<DataEntity> queryHighlightIndex(int startOfPage, int numberOfPage, String keywords) throws SolrServerException, IOException {
        SolrQuery q = new SolrQuery();
        //开始页数
        q.setStart(startOfPage);
        //每页显示条数
        q.setRows(numberOfPage);
        // 设置查询关键字
        q.setQuery("user:*" + keywords + "*");
        // 开启高亮
        q.setHighlight(true);
        // 高亮字段
        q.addHighlightField("user");

        // 高亮单词的前缀
        q.setHighlightSimplePre("<span style='color:red'>");
        // 高亮单词的后缀
        q.setHighlightSimplePost("</span>");
        //摘要最长100个字符
        q.setHighlightFragsize(10000);
        //查询
        QueryResponse query = client.query(q);

        // 查询结果
        SolrDocumentList docs = query.getResults();
        //获取查询到的个数
        String numFound = String.valueOf(docs.getNumFound());
        //打印查询结果
        printResults(docs, numFound, keywords);

        // 高亮结果
        Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();

        //集合对象
        List<DataEntity> arr = new ArrayList<>();
        //获取查询结果
        SolrDocumentList results = query.getResults();
        for (SolrDocument result : results) {
            System.out.println(result.toString());
            DataEntity dataEntity = new DataEntity(result, highlighting.get(result.get("id")).get("user").get(0));
            arr.add(dataEntity);
        }
        return arr;
    }



    /**
     * 查询总数
     *
     * @param keywords
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public static int queryCount(String keywords) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        if ("".equals(keywords) || keywords == null) {
            query.setQuery("*:*");
        } else {
            query.setQuery("user:*" + keywords + "*");
        }
        QueryResponse queryResponse = client.query(query);
        SolrDocumentList results = queryResponse.getResults();

        return Integer.parseInt(results.getNumFound() + "");
    }


    /**
     * 批量保存索引
     *
     * @param entities
     * @param <T>
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public static <T> boolean batchSaveOrUpdate(List<T> entities) throws SolrServerException, IOException {

        DocumentObjectBinder binder = new DocumentObjectBinder();
        int total = entities.size();
        int count = 0;
        for (T t : entities) {
            SolrInputDocument doc = binder.toSolrInputDocument(t);
            client.add(doc);
            System.out.printf("添加数据到索引中，总共要添加 %d 条记录，当前添加第%d条 %n", total, ++count);
        }
        client.commit();
        return true;
    }


    /**
     * 打印索引查询结果
     *
     * @param results  文档结果集
     * @param numFound 匹配结果数
     * @param keyword  关键词
     */
    private static void printResults(SolrDocumentList results, String numFound, String keyword) {
        System.out.printf("一共找到了 %s 条", numFound);
        System.out.printf("关键词为 %s", keyword);
        System.out.println();
        if (!results.isEmpty()) {
            Collection<String> fieldNames = results.get(0).getFieldNames();
            for (String fieldName : fieldNames) {
                System.out.print(fieldName + "\t");
            }
            System.out.println();
        }

        for (SolrDocument result : results) {
            Collection<String> fieldNames = result.getFieldNames();
            for (String fieldName : fieldNames) {
                Object o = result.get(fieldName);
                System.out.print(o + "\t");
            }
            System.out.println();
        }
    }


    /**
     * 更新/添加一个索引
     *
     * @param entity
     * @param <T>
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public static <T> boolean saveOrUpdate(T entity) throws SolrServerException, IOException {
        DocumentObjectBinder binder = new DocumentObjectBinder();
        SolrInputDocument doc = binder.toSolrInputDocument(entity);
        client.add(doc);
        client.commit();
        return true;
    }

    /**
     * 根据id删除一个索引
     *
     * @param id
     * @return
     */
    public static boolean deleteById(String id) {
        try {
            client.deleteById(id);
            client.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除所有索引
     *
     * @return
     */
    public static boolean deleteAll() {
        try {
            client.deleteByQuery("*:*");
            client.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
