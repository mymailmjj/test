/**
 * 
 */
package springtest.dao;

/**
 * @author cango
 *
 */
public class TranServiceImpl implements TranService{
    
    public void openConnection() {
        System.out.println("TranService open connection");
    }   

    public void closeConnection() {
        System.out.println("TranService close connection");
    }
    

}
