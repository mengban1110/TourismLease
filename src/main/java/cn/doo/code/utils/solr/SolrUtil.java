package cn.doo.code.utils.solr;


import cn.doo.code.utils.solr.entity.DataEntity;
import cn.doo.code.utils.solr.service.DataService;
import com.alibaba.druid.util.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 梦伴
 * @desc FlushIndex
 * @time 2021-05-07-10:43
 */
@Component
public class SolrUtil {

    private static SolrUtil SolrUtil;

    @Autowired
    private DataService dataService;

    @PostConstruct
    public void init() throws IOException, SolrServerException, SQLException, ClassNotFoundException {
        SolrUtil = this;
        SolrUtil.dataService = this.dataService;
        buildIndex();
    }

    /**
     * 初始化索引
     *
     * @throws IOException
     * @throws SolrServerException
     */
    private void buildIndex() throws IOException, SolrServerException, SQLException, ClassNotFoundException {
        SolrBaseModules.deleteAll();
        List<DataEntity> dataEntities = SolrUtil.dataService.initIndex();
        System.out.println("dataEntities = " + dataEntities);
        SolrBaseModules.batchSaveOrUpdate(dataEntities);
    }

    /**
     * qz周期刷新索引
     *
     * @throws IOException
     * @throws SolrServerException
     */
    public static void quartzJob() throws IOException, SolrServerException, SQLException, ClassNotFoundException {
        List<DataEntity> dataEntities = SolrUtil.dataService.initIndex();
        System.out.println("dataEntities = " + dataEntities);
        SolrBaseModules.batchSaveOrUpdate(dataEntities);
    }


    /**
     * 查询索引(带有高亮)
     *
     * @param keyword 关键词
     * @param page    当前页
     * @param size    每页个数
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public List<DataEntity> queryIndexItems(Integer page, Integer size, String keyword) throws SolrServerException, IOException {

        if (page == 0) {
            page = 1;
        }
        page = (page - 1) * size;

        List<DataEntity> arr;
        if (StringUtils.isEmpty(keyword)) {
            arr = SolrBaseModules.queryAllIndex("*:*", page, size);
        } else {
            arr = SolrBaseModules.queryHighlightIndex(page, size, keyword);
        }
        return arr;
    }


    /**
     * 匹配总数查询
     *
     * @param keyword
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public int queryIndexCount(String keyword) throws SolrServerException, IOException {
        return SolrBaseModules.queryCount(keyword);
    }



}
