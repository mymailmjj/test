package datasource;

import java.util.Properties;

import javax.sql.DataSource;

public class BasicDataSourceFactory {
    
    
    private Properties properties = null;
    
    public static DataSource createDataSource(Properties prop){
        
        BasicDataSource basicDataSource = new BasicDataSource();
        
        return basicDataSource;
    }
    

}
