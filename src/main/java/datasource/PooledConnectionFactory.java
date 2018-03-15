/**
 * 
 */
package datasource;

import java.sql.Connection;

/**
 * @author cango
 *
 */
public class PooledConnectionFactory {
    
    private ConnectionFactory connectionFactory;
    
    private PoolBuffer poolBuffer;
    
    public PooledConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    
    public PooledConnectionFactory(ConnectionFactory connectionFactory, PoolBuffer poolBuffer) {
        this(connectionFactory);
        this.poolBuffer = poolBuffer;
    }

    public Connection createPooledConnection(){
        
        Connection createConnection = connectionFactory.createConnection();
        
        PooledConnection pc = new PooledConnection(createConnection,poolBuffer);
        
        return pc;
    }

}
