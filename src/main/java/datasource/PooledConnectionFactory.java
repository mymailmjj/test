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
    
    public static Connection createConnection(){
        return new PooledConnection();
    }

}
