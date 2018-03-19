package datasource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import junit.framework.TestCase;

public class Main extends TestCase{

    public static void main(String[] args) {
        
        Properties properties = new Properties();
        
        InputStream inputStream = Main.class.getResourceAsStream("/datasource/jdbc.properties");
        
        try {
            properties.load(inputStream);
            
        System.out.println(properties);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    
    public void testPooledObject(){
        
        Properties properties = new Properties();
        
        InputStream inputStream = Main.class.getResourceAsStream("/datasource/jdbc.properties");
        
        try {
            properties.load(inputStream);
            
        System.out.println(properties);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        DefaultPoolBuffer<PooledObject<Connection>> poolBuffer = new DefaultPoolBuffer<>();
        
        String url = properties.getProperty("url");
        
        ConnectionFactory connectionFactory = new DriverConnectionFactory(url, properties);
        
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
        
        PooledObjectFactory<Connection,PooledObject<Connection>> pooledObjectFactory = new PooledObjectFactory<>(pooledConnectionFactory,poolBuffer);
        
        poolBuffer.setPooledObjectFactory(pooledObjectFactory);
        
        PooledObject<Connection> borrowObject = poolBuffer.borrowObject();
        
        System.out.println(borrowObject);
        
    }
    
    
    public void testMyDataBaseSource(){
        
        Properties properties = new Properties();
        
        InputStream inputStream = Main.class.getResourceAsStream("/datasource/jdbc.properties");
        
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        
        Thread[] thread = new Thread[20];
            
            for(int i = 0;i <20; i++){
                
                thread[i] = new Thread(new Runnable() {
                    
                    @Override
                    public void run() {
                        
                            Connection connection = null;
                            try {
                                connection = dataSource.getConnection();
                                System.out.println("获得连接："+connection);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }finally{
                                try {
                                    connection.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                });
               
            }
           
            for(int i = 0;i <20; i++){
                Thread t = thread[i];
                t.start();
            }
            
            for(int i = 0;i <20; i++){
                try {
                    thread[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
    }

}
