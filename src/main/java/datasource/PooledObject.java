/**
 * 
 */
package datasource;

import java.sql.Connection;
import java.time.LocalDateTime;

/**
 * @author cango
 *
 */
public class PooledObject<T extends Connection> {

    //主对象
    private T t;
    
    private PooledObjectStatus status;
    
    private LocalDateTime localDateTime = LocalDateTime.now();
    
    private LocalDateTime usingTime = LocalDateTime.now();
    
    private LocalDateTime closingTime = LocalDateTime.now();
    
    
    public PooledObject(T t){
        this.t = t;
    }
    
    public void markCreated(){
        this.status = PooledObjectStatus.CREATED;
    }
    
    public void markUsing(){
        this.status = PooledObjectStatus.USING;
    }
    
    
}


