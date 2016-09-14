package bigdata.smartsafety.master.xliu.common;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by xliu on 2016/9/5.
 */
public class MySQLUtil {
    private static Logger logger=Logger.getLogger(MySQLUtil.class);
    private static final ThreadLocal<Connection> planeConn=new ThreadLocal<Connection>();
    private static Connection getConnection(String source) {
        ThreadLocal<Connection> connectionThreadLocal = null;
        if(source.equals("plane")){
            connectionThreadLocal=planeConn;
        }else{
            return null;
        }
        try {
            Connection conn = connectionThreadLocal.get();
            if (conn == null || !conn.isValid(100)) {
                conn = connect(source);
                connectionThreadLocal.set(conn);
            }
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Connection connect(String source) throws IOException, ClassNotFoundException, SQLException {
        Properties pro=new Properties();
        pro.load(new FileInputStream(Const.MYSQL_PROPERTIES));
        String driver=pro.getProperty(source+".driver");
        String url=pro.getProperty(source+".url");
        String user=pro.getProperty(source+".user");
        String password=pro.getProperty(source+".password");
        Class.forName(driver);
        logger.debug(String.format("url=%s,user=%s,psw=%s",url,user,password));
        Connection conn=DriverManager.getConnection(url,user,password);
        return conn;
    }

    public static ResultSet getResult(String source,String sql) throws SQLException {
        logger.info(String.format("query [%s]",sql));
        Connection conn=getConnection(source);
        Statement stat=conn.createStatement();
        return stat.executeQuery(sql);
    }

    public static void updateResult(String source,String sql) throws SQLException {
        logger.info(String.format("update [%s]",sql));
        Connection conn=getConnection(source);
        Statement stat=conn.createStatement();
        stat.executeUpdate(sql);
    }
}
