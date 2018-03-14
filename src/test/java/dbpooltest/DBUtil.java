package dbpooltest;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class DBUtil {
    
    private static BasicDataSource basicDataSource = null;
    
    static{
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            InputStream inputStream = Main.class.getResourceAsStream("/jdbc.properties");
        
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            basicDataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static Connection  openConnection() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mariadb://192.168.121.198:3306/newerp2?autoReconnect=true&amp;autoReconnectForPools=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", "devuser","12345");
        return connection;
    }
    
    public static void closeConnection(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static Connection  openConnectionWithDBCP() throws SQLException{
        return basicDataSource.getConnection();
    }

}
