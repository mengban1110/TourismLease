package cn.doo.code.utils.solr.dao;

import cn.doo.code.utils.solr.entity.DataEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;
import java.util.List;

public interface Dao {

	/**
	 * 根据sql查询列表数据(查询多条)，不支持预编译的方式
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<DataEntity> executeQueryForList() throws ClassNotFoundException, SQLException, JsonProcessingException;

}
