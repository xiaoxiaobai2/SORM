package core;

import bean.Configuration;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 根据配置信息，维持连接对象的管理
 * @author 张浩
 */
public class DBManger {
    private static Configuration conf;
    static {
        Properties properties=new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf=new Configuration();
        conf.setDriver(properties.getProperty("Driver"));
        conf.setURL(properties.getProperty("URL"));
        conf.setUser(properties.getProperty("User"));
        conf.setPwd(properties.getProperty("Pwd"));
        conf.setUsingDB(properties.getProperty("UsingDB"));
        conf.setSrcPath(properties.getProperty("SrcPath"));
        conf.setPoPackages(properties.getProperty("PoPackages"));
    }

    /**
     * 连接数据库
     * @return 连接对象
     */
    public static Connection getConn(){
        Connection connection=null;
        try {
            //加载驱动类
            Class.forName(conf.getDriver());
            //连接数据库
            connection = DriverManager.getConnection(conf.getURL(), conf.getUser(), conf.getPwd());
        } catch (ClassNotFoundException e) {
            System.err.println("驱动类加载失败！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭各种文件
     * @param c
     * @param ps
     * @param set
     */
    public static void close(Connection c, PreparedStatement ps, ResultSet set){
        if (set!=null){
            try {
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (c!=null){
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static Configuration getConf(){
        return conf;
    }
}
