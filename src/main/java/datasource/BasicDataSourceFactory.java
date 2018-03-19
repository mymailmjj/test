package datasource;

import java.util.Properties;

import javax.sql.DataSource;

public class BasicDataSourceFactory {
    
    private static volatile DataSource dataSource = null;
    
    /**
     * 根据加载的属性类
     * @param prop
     * @return
     */
    public static synchronized DataSource createDataSource(Properties prop){
        
        if(BasicDataSourceFactory.dataSource!=null) return dataSource;
        
        BasicDataSource basicDataSource = new BasicDataSource();
        
        basicDataSource.setDriverClass(prop.getProperty("driverClassName"));
        
        basicDataSource.setUrl(prop.getProperty("url"));
        
        basicDataSource.setUserName(prop.getProperty("username"));
        
        basicDataSource.setPassword(prop.getProperty("password"));
        
        String initialSize = prop.getProperty("initialSize");
        
        if(initialSize!=null){
            int isize = Integer.valueOf(initialSize);
            basicDataSource.setInitialSize(isize);
        }
        
        String maxTotal = prop.getProperty("maxTotal");
        
        if(maxTotal!=null){
            int maTotal = Integer.valueOf(maxTotal);
            basicDataSource.setMaxTotal(maTotal);
        }
        
        String maxIdle = prop.getProperty("maxIdle");
        
        if(maxIdle!=null){
            int maIdle = Integer.valueOf(maxIdle);
            basicDataSource.setMaxIdle(maIdle);
        }
        
        String minIdle = prop.getProperty("minIdle");
        
        if(minIdle!=null){
            int maIdle = Integer.valueOf(minIdle);
            basicDataSource.setMinIdle(maIdle);
        }
        
        String maxWaitMillis = prop.getProperty("maxWaitMillis");
        
        if(maxWaitMillis!=null){
            int mWaitMillis = Integer.valueOf(maxWaitMillis);
            basicDataSource.setMaxWaitMillis(mWaitMillis);
        }
        
        
        String maxWait = prop.getProperty("maxWait");
        
        if(maxWait!=null){
            int maWait = Integer.valueOf(maxWait);
            basicDataSource.setMaxWait(maWait);
        }
        
        dataSource = basicDataSource;
        
        return basicDataSource;
    }
    

}
