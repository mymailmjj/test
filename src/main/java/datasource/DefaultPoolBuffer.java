package datasource;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class DefaultPoolBuffer<T extends PooledObject<Connection>> implements PoolBuffer<T>{
    
    private PooledObjectFactory<Connection,T> pooledObjectFactory;

    public DefaultPoolBuffer() {

    }
    
    public DefaultPoolBuffer(PooledObjectFactory<Connection,T> pooledObjectFactory){
        this.pooledObjectFactory = pooledObjectFactory;
    }

    private LinkedBlockingDeque<T> idleQueue = new LinkedBlockingDeque<T>();
    
    private LinkedHashMap<Connection, T> allMaps = new LinkedHashMap<>();
    
    public void pushObject(T t) {
        this.idleQueue.addFirst(t);
    }
    
    public PooledObjectFactory<Connection, T> getPooledObjectFactory() {
        return pooledObjectFactory;
    }

    public void setPooledObjectFactory(PooledObjectFactory<Connection, T> pooledObjectFactory) {
        this.pooledObjectFactory = pooledObjectFactory;
    }

    public LinkedBlockingDeque<T> getIdleQueue() {
        return idleQueue;
    }

    public void setIdleQueue(LinkedBlockingDeque<T> idleQueue) {
        this.idleQueue = idleQueue;
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
        
        return con;
    }
    
    public void addObject(T t){
        
        
        
    }

    
    
    @SuppressWarnings("unchecked")
    public T create() {
        
        System.out.println("create:");
        
        PooledObject<Connection> newPooledObject = pooledObjectFactory.newPooledObject();
        
        allMaps.put(newPooledObject.getObject(), (T) newPooledObject);
        
        System.out.println(allMaps);
        
        return (T) newPooledObject;
    }
    
    
    public void validateObject(T t){
        
    }


    @Override
    public <U extends Connection> void close(U u) {
        
        System.out.println(u);
        
        T t = allMaps.get(u);
        
        System.out.println("取到的t:"+t);
        
    }
    

}
