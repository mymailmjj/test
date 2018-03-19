package datasource;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultPoolBuffer<T extends PooledObject<Connection>> implements PoolBuffer<T> {

    private PooledObjectFactory<Connection, T> pooledObjectFactory;
    
    private Lock lock = new ReentrantLock();

    public DefaultPoolBuffer() {

    }

    public DefaultPoolBuffer(PooledObjectFactory<Connection, T> pooledObjectFactory) {
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

    public synchronized T borrowObject() {
        // 先从缓存里面取
        T con = (T) idleQueue.pollLast();

        // 如果缓存存在，则出去
        if (con != null) {
            System.out.println("缓存里面去con:" + con);
            return con;
        }

        // 不存在，则生成
        con = create();

        // 最后校验
        validateObject(con);

        return con;
    }

    public void addObject(T t) {

    }

    @SuppressWarnings("unchecked")
    public T create() {

        PooledObject<Connection> newPooledObject = pooledObjectFactory.newPooledObject();

        System.out.println("create new:" + newPooledObject.getObject());

        allMaps.put(newPooledObject.getObject(), (T) newPooledObject);

        return (T) newPooledObject;
    }

    public void validateObject(T t) {

    }

    @Override
    public synchronized <U extends Connection> void close(U u) {

        T t = allMaps.get(u);

        idleQueue.push(t);

        System.out.println("start print idleQueue:---------------------------\\");

        idleQueue.forEach((T x) -> {
            System.out.println(((PooledObject) x).getObject());
        });

        System.out.println("end----------------------");

    }

}
