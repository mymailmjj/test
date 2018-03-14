package datasource;

import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class PoolBuffer<T extends PooledObject<Connection>> {


    public PoolBuffer() {

    }

    private LinkedBlockingDeque<T> idleQueue = new LinkedBlockingDeque<T>();
    
    private LinkedBlockingDeque<T> allQueue = new LinkedBlockingDeque<T>();
    
    public void pushObject(T t) {
        this.idleQueue.addFirst(t);
    }
    
    
    public T borrowObject(){
        //先从缓存里面取
        T con = (T) idleQueue.pollLast();
        
        //如果缓存存在，则出去
        if(con!=null) return con;
        
        //不存在，则生成
        con = create();
        
        //最后校验
        validateObject(con);
        
        return (T) idleQueue.pollLast();
    }

    
    
    private T create() {
        return null;
    }
    
    
    private void validateObject(T t){
        
    }
    
    

}
