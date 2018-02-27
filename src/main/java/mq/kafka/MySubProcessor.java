/**
 * 
 */
package mq.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cango
 *
 */
public class MySubProcessor implements Processor<String,String> {
    
    Logger loger = LoggerFactory.getLogger(MySubProcessor.class);
    
    
    private ProcessorContext context;
    
    private String processName;
    
    public MySubProcessor(String processName) {
        this.processName = processName;
    }


    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        
    }


    @Override
    public void punctuate(long timestamp) {
        
    }

    @Override
    public void close() {
        
    }


    @Override
    public void process(String key, String value) {
        if(value!=null){
            String v = new String(value);
            loger.info("process:"+this.processName+"\tprocessing values:"+v);
            loger.info("values:"+ v);
        }
    }


}
