/**
 * 
 */
package springframework;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author cango
 *
 */
public class SpringMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:springtest.xml");
        
        Test bean = (Test) classPathXmlApplicationContext.getBean("test1");
        
        Test test = bean.getTest();
        
        System.out.println("bean:"+bean);
        
        System.out.println("test2:"+test);

    }

}
