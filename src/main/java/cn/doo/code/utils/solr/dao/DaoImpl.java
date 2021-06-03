package cn.doo.code.utils.solr.dao;

import cn.doo.code.utils.solr.entity.DataEntity;
import cn.doo.code.utils.solr.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DaoImpl implements Dao {

    private String sDBDriver = "org.gjt.mm.mysql.Driver";
    private String sConnStr = "jdbc:mysql://127.0.0.1:3306/TourismLease?useUnicode=true&characterEncoding=utf-8&useOldAliasMetadataBehavior=true";
    private String username = "root"; // 登录数据库用户名
    private String password = "root"; // 登录数据库密码

//	private static String sDBDriver = null;
//	private static String sConnStr = null;
//	private static String username = null;
//	private static String password = null;

//	static {
//		try {
//			Properties prop = new Properties();
//			ResourceBundle myResources = ResourceBundle.getBundle("cn.DoO.Config.mysqlconnection");
//			sDBDriver = myResources.getString("driver");
//			sConnStr = myResources.getString("connect");
//			username = myResources.getString("username");
//			password = myResources.getString("password");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}

    /**
     * 建立连接
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName(sDBDriver); // 指定JDBC数据库驱动程序

        return DriverManager.getConnection(sConnStr, username, password);
    }


    /**
     * 根据sql查询列表数据，不支持预编译的方式
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public List<DataEntity> executeQueryForList() throws ClassNotFoundException, SQLException, JsonProcessingException {
        String sql = "SELECT lease.id ,lease.leaseinfo,tenant.id uid,tenant.`name`,tenant.phone,lease.createtime,lease.endtime,lease.deposit,lease.rent,lease.breakinfo,lease.`status` FROM lease LEFT JOIN tenant on lease.uid = tenant.id";
        System.err.println("查询多条：" + sql);
        Connection connect = this.getConnection();
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<DataEntity> list = this.rsToList(rs);
        this.releaseConnection(rs, stmt, connect);// 关闭连接
        return list;
    }


    /**
     * 将ResultSet中的结果包装成list中装Map的结构
     *
     * @param rs
     * @return
     * @throws SQLException
     * @author wyh
     * @time 2019-05-11
     */
    private List<DataEntity> rsToList(ResultSet rs) throws SQLException, JsonProcessingException {
        List<DataEntity> row = new ArrayList<DataEntity>();
        while (rs.next()) {
            Map<String, Object> col = new HashMap<String, Object>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                // System.out.println( rs.getMetaData().getColumnType(i) );
                switch (rs.getMetaData().getColumnType(i)) {
                    case Types.VARCHAR:
                        col.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                        break;
                    case Types.INTEGER:
                        col.put(rs.getMetaData().getColumnName(i), rs.getInt(i));
                        break;
                    case Types.DATE:
                        col.put(rs.getMetaData().getColumnName(i), rs.getDate(i));
                        break;
                    default:
                        col.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                        break;
                }
            }

            DataEntity dataEntity = getEntity(col);

            row.add(dataEntity);
        }
        return row;
    }

    /**
     * 封装对象
     *
     * @param col
     * @return
     */
    private DataEntity getEntity(Map<String, Object> col) throws JsonProcessingException {
        Object id = col.get("id");
        Object leaseinfo = col.get("leaseinfo");

        /**
         * user对象
         */
        Object uid = col.get("uid");
        Object name = col.get("name");
        Object phone = col.get("phone");

        Object createtime = col.get("createtime");
        Object endtime = col.get("endtime");
        Object deposit = col.get("deposit");
        Object rent = col.get("rent");
        Object breakinfo = col.get("breakinfo");
        Object status = col.get("status");

        /**
         * 创建对象
         */
        DataEntity dataEntity = new DataEntity();

        dataEntity.setId(id + "");
        dataEntity.setLeaseinfo(leaseinfo + "");
        dataEntity.setUser(getUserJson(uid, name, phone));
        dataEntity.setCreatetime(createtime+"");
        dataEntity.setEndtime(endtime+"");
        dataEntity.setDeposit(Integer.parseInt(deposit+""));
        dataEntity.setRent(Integer.parseInt(rent+""));
        dataEntity.setBreakinfo(breakinfo+"");
        dataEntity.setStatus(Integer.parseInt(status+""));
        return dataEntity;
    }

    /**
     * 获取userJson
     * @param uid
     * @param name
     * @param phone
     * @return
     * @throws JsonProcessingException
     */
    private String getUserJson(Object uid, Object name, Object phone) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setUid(Integer.parseInt(uid +""));
        user.setName(name +"");
        user.setPhone(phone +"");
        return objectMapper.writeValueAsString(user);
    }


    @SuppressWarnings("unused")
    private void releaseConnection(Connection connect) throws SQLException {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.close();
            }
        } catch (SQLException se) {
            System.out.println("Close the connection encounter error!\n" + se.getMessage());
            throw new SQLException("关闭连接异常！");
        }
    }

    private void releaseConnection(ResultSet rs, Statement stmt, Connection connect) throws SQLException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connect != null && !connect.isClosed()) {
                connect.close();
            }
        } catch (SQLException se) {
            System.out.println("Close the connection encounter error!\n" + se.getMessage());
            throw new SQLException("关闭连接异常！");
        }
    }

}
