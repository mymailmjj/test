/**
 * 
 */
package mq.kafka;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.processor.Punctuator;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cango
 *
 */
public class MyProcessor implements Processor<byte[],byte[]> {
    
    Logger loger = LoggerFactory.getLogger(MyProcessor.class);
    
    /**
     * 每隔一段时间去重新统计一次结果
     * @author cango
     *
     */
    private class MyPunctuate implements Punctuator{

        @Override
        public void punctuate(long timestamp) {
            KeyValueIterator<String,String> iter = kvStore.all();

            while (iter.hasNext()) {
                KeyValue<String,String> entry = (KeyValue) iter.next();
                context.forward(entry.key, entry.value.toString());   //调用子流程
            }

            iter.close();
            context.commit();
            
        }
        
    }
    
    
    private ProcessorContext context;
    /**
     * 临时存储结果
     */
    private KeyValueStore<String,String> kvStore;
    
    private String processName;
    
    public MyProcessor(String processName) {
        this.processName = processName;
    }


    @Override
    public void init(ProcessorContext context) {
        this.context = context;
//        this.context.schedule(300000, PunctuationType.WALL_CLOCK_TIME, new MyPunctuate());
        this.kvStore = (KeyValueStore) context.getStateStore(processName);
        
    }


    @Override
    public void punctuate(long timestamp) {
        
    }

    @Override
    public void close() {
        this.kvStore.close();
    }


    @Override
    public void process(byte[] key, byte[] value) {
        if(value!=null){
            String v = new String(value);
            loger.info("process:"+this.processName+"\tprocessing values:"+v);
            loger.info("values:"+ v);
            kvStore.put(v, v);
            context.forward(key, v);
        }
        
    }


}
