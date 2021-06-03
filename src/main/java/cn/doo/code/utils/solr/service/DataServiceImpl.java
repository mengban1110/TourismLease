package cn.doo.code.utils.solr.service;

import cn.doo.code.utils.solr.dao.Dao;
import cn.doo.code.utils.solr.entity.DataEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 梦伴
 * @desc
 * @time 2021-05-26-8:18
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private Dao daoImpl;

    /**
     * 建立索引
     *
     * @return
     */
    @Override
    public List<DataEntity> initIndex() throws SQLException, ClassNotFoundException, JsonProcessingException {
        return daoImpl.executeQueryForList();
    }
}
