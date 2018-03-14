package dbpooltest;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class Main {
    
    public static void main(String[] args) {
        
        InputStream inputStream = Main.class.getResourceAsStream("/jdbc.properties");
        
        Properties properties = new Properties();
        
        try {
            properties.load(inputStream);
            
            System.out.println(properties);
            
            BasicDataSource basicDataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
            
            Connection connection = basicDataSource.getConnection();
            
//            ID
//            type
//            APIREQPARAMS
//            APIREQURL
//            APIRES
//            INSERTTIME
//            UPDATETIME
//            userId
            
            PreparedStatement ps = connection.prepareStatement("SELECT * from t_exam_apply where DFIMUSER = 29019 order by LASTUPDATETIME DESC limit 10");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int int1 = rs.getInt(1);
                System.out.println("id:"+int1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

}
