package datasource;

import java.sql.Connection;

public interface PoolBuffer<T extends PooledObject<? super Connection>> {

    public T borrowObject();
    
    public void validateObject(T t);
    
    public T create();
    
    public <U extends Connection> void close(U u);
    
    public void addObject(T t);
    
}
