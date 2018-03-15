package datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class BasicDataSource implements DataSource{
    
    private String driverClass;
    
    private String url;
    
    private String userName;
    
    private String password;
    
    /**
     * #初试连接数 默认为0
     */
    private int initialSize = 0;
    
    /**
     * #最大活跃数  超过默认值后，允许的最大值
     */
    private int maxTotal;
    
    /**
     * #最大idle数 允许空闲的数目，超过这个则会被回收
     */
    private int maxIdle;
    
    /**
     * #最小idle数  最少的需要的数目
     */
    private int minIdle;
    
    /**
     * #最长等待时间(毫秒)
     */
    private int maxWaitMillis;
    
    /**
     * 最大等待时间
     */
    private int maxWait;
    
    /**
     * 核心的对象容器
     */
    private DefaultPoolBuffer poolBuffer = null;
    
    public BasicDataSource(DefaultPoolBuffer poolBuffer) {
        this.poolBuffer = poolBuffer;
    }
    
    
    
    public BasicDataSource() {
    
    }
    
    private DataSource datasource;

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new IllegalArgumentException("Basic DataSource is not a Wrap");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        
        return createPoolDataSource().getConnection();
    }
    

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        
        return null;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public DefaultPoolBuffer getPoolBuffer() {
        return poolBuffer;
    }

    public void setPoolBuffer(DefaultPoolBuffer poolBuffer) {
        this.poolBuffer = poolBuffer;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
    
    private DataSource createPoolDataSource(){
        
        if(this.datasource!=null){
            return this.datasource;
        }
        
        PooledDataSource pooledDataSource = new PooledDataSource();
        
        DefaultPoolBuffer<PooledObject<Connection>> poolBuffer = new DefaultPoolBuffer<>();
        
        ConnectionFactory connectionFactory = new DriverConnectionFactory(url, userName,password);
        
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory,poolBuffer);
        
        PooledObjectFactory<Connection,PooledObject<Connection>> pooledObjectFactory = new PooledObjectFactory<>(pooledConnectionFactory,poolBuffer);
        
        poolBuffer.setPooledObjectFactory(pooledObjectFactory);
        
        pooledDataSource.setPoolBuffer(poolBuffer);
        
        this.datasource = pooledDataSource;
        
        return pooledDataSource;
    }
}
