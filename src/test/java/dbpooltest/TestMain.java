package dbpooltest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import junit.framework.TestCase;

public class TestMain extends TestCase{

    /**
     * 每次建立一个连接，测试2000多次循环的时间
     * 共消耗135秒 
     */
    public void testLoopConnection(){
        
        long start = System.currentTimeMillis();
        
        for(int i = 0; i< 2000;i++){
        
        Connection connection =  null;
        
        try {
            connection = DBUtil.openConnection();
            
           PreparedStatement ps = connection.prepareStatement("insert into t_car_api_log_bak (ID ,type, APIREQPARAMS,APIREQURL,APIRES,INSERTTIME,UPDATETIME,userId )"
                    + " values (?,?,?,?,?,?,?,?)");
            
            ps.setInt(1, 1);
            ps.setString(2, "a");
            ps.setString(3, "b");
            ps.setString(4, "c");
            ps.setString(5, "c");
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps.setInt(8, 8);
            
            ps.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(connection);
        }
        
        }
        
        long end = System.currentTimeMillis();
        
        System.out.println((end-start)+"ms");
        
    }
    
    
    /**
     * 使用dbcp处理同上的问题，对比时间消耗
     */
    public void testLoopWithDBCP(){
        
        long start = System.currentTimeMillis();
        
        for(int i = 0; i< 2000;i++){
        
        Connection connection =  null;
        
        try {
           connection = DBUtil.openConnectionWithDBCP();
           
           System.out.println(connection+"\t"+i);
            
           PreparedStatement ps = connection.prepareStatement("insert into t_car_api_log_bak (ID ,type, APIREQPARAMS,APIREQURL,APIRES,INSERTTIME,UPDATETIME,userId )"
                    + " values (?,?,?,?,?,?,?,?)");
            
            ps.setInt(1, 1);
            ps.setString(2, "a");
            ps.setString(3, "b");
            ps.setString(4, "c");
            ps.setString(5, "c");
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps.setInt(8, 8);
            
            ps.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(connection);
        }
        
        }
        
        long end = System.currentTimeMillis();
        
        System.out.println((end-start)+"ms");
    }

}
