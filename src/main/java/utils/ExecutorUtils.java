/**
 * 
 */
package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author cango
 *
 */
public class ExecutorUtils {
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    public static void executor(Runnable r,int num){
        
        for(int i = 0; i<num; i++){
            executorService.execute(r);
        }
        
        executorService.shutdown();
    }

}
