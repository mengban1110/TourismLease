package cn.doo.code.utils.solr.service;

import cn.doo.code.utils.solr.entity.DataEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 梦伴
 * @desc
 * @time 2021-05-26-8:18
 */
public interface DataService {
    /**
     * 建立索引
     * @return
     */
    List<DataEntity> initIndex() throws SQLException, ClassNotFoundException, JsonProcessingException;
}
