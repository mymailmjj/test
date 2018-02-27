package springtest.dao;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 了解spring 如果构造代理对象的过程
 * 
 * 典型配置方式   
 * <aop:config>
       <aop:pointcut id="pointcut" expression="(execution(* springtest.dao..*.find*(..)))"/>
       <aop:aspect ref="tranService">
            <aop:before method="openConnection" pointcut-ref="pointcut"/>
            <aop:after method="closeConnection" pointcut-ref="pointcut"/>
        </aop:aspect>
  </aop:config>
  
  在AopNamespaceHandler中定义了解析器ConfigBeanDefinitionParser
  不同的节点对应不同的class，影响bean的解析过程
  <aop:config>      AspectJAwareAdvisorAutoProxyCreator.class
    pointcut        AspectJExpressionPointcut.class

    <aop:aspect ref="tranService">     
    advice               DefaultBeanFactoryPointcutAdvisor.class
       aop:before        AspectJMethodBeforeAdvice
       aop:after         AspectJAfterAdvice
       
    核心类在AbstractAutoProxyCreator 
    
    生成代理对象的方法：
        当存在<aop:config>标签时，在容器中注入AbstractAutoProxyCreator.class对象，它实现了
    SmartInstantiationAwareBeanPostProcessor 接口，让在目标对象被实例化时，调用到beanpostprocessor接口之后，
        在postProcessAfterInitialization方法中会调用wrapIfNecessary方法会获取该对象匹配的所有的advice。
    AbstractAutoProxyCreator对象里面包含了BeanFactoryAdvisorRetrievalHelper工具类，
        用于从beanfactory中取出所有advice（AspectJAfterAdvice对象），该对象里面的aspectName包含了其作用的对象。存在advice则生成代理对象。
    
 * 
 * @author mujianjiang
 *
 */
public class MainTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:springtest.xml");
        TranService tranService = (TranService) classPathXmlApplicationContext.getBean("tranService");
        System.out.println("tranService:"+tranService);
        
        
        String[] beanDefinitionNames = classPathXmlApplicationContext.getBeanDefinitionNames();
        
       
        
        for (int i = 0; i < beanDefinitionNames.length; i++) {
            System.out.println("beanName："+beanDefinitionNames[i]);
        }
        
        
        TestDao testDao = (TestDao) classPathXmlApplicationContext.getBean("testDao");
        
        Class<? extends TestDao> class1 = testDao.getClass();
        
        Method[] declaredMethods = class1.getDeclaredMethods();
        
        for (int i = 0; i < declaredMethods.length; i++) {
            Method method = declaredMethods[i];
            System.out.println("method:"+method.getName());
        }
        
        
        testDao.find("mujianjiang");
        
        Map maps = classPathXmlApplicationContext.getBeansOfType(AspectJPointcutAdvisor.class);
        
        Set keySet = maps.keySet();
        
        Iterator iterator = keySet.iterator();
        
        while(iterator.hasNext()){
            String next = (String) iterator.next();
            AspectJPointcutAdvisor object = (AspectJPointcutAdvisor) maps.get(next);
            ComposablePointcut e = (ComposablePointcut) object.getPointcut();
            System.out.println(e.getMethodMatcher());
        }
        
        
    }

}
