package datasource;

import java.sql.Connection;


public class PooledObjectFactory<T extends Connection> {
    
    private static PooledConnectionFactory pooledConnectionFactory;
    
    public PooledObjectFactory(PooledConnectionFactory pooledConnectionFactory) {
        pooledConnectionFactory = pooledConnectionFactory;
    }

    public static <T extends Connection> PooledObject<T> newPooledObject(){
        
        Connection connection = pooledConnectionFactory.createConnection();
        
        PooledObject<T> pooledObject = new PooledObject(connection);
        
        return pooledObject;
    }

}
