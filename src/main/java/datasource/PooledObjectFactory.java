package datasource;

import java.sql.Connection;


public class PooledObjectFactory<T extends Connection,U extends PooledObject<? super Connection>> {
    
    private PooledConnectionFactory pooledConnectionFactory;
    
    public PooledObjectFactory(PooledConnectionFactory pooledConnectionFactory) {
        this.pooledConnectionFactory = pooledConnectionFactory;
    }

    private PoolBuffer<U> poolBuffer = null;
    
    public PooledObjectFactory(PoolBuffer<U> poolBuffer) {
        this.poolBuffer = poolBuffer;
    }
    
    
    public PooledObjectFactory(PooledConnectionFactory pooledConnectionFactory, PoolBuffer<U> poolBuffer) {
        this.pooledConnectionFactory = pooledConnectionFactory;
        this.poolBuffer = poolBuffer;
    }



    public PooledObject<T> newPooledObject(){
        
        Connection connection = pooledConnectionFactory.createPooledConnection();
        
        PooledObject<T> pooledObject = new PooledObject(connection);
        
        return pooledObject;
    }

}
