/**
 * 
 */
package mq.kafka;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

/**
 * @author cango
 *
 */
public class MyProcessor implements Processor<String,String> {
    
    private ProcessorContext context;
    private KeyValueStore<String,String> kvStore;
    
    private String processName;
    
    public MyProcessor(String processName) {
        this.processName = processName;
    }


    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.kvStore = (KeyValueStore) context.getStateStore("Counts");
        
    }


    @Override
    public void punctuate(long timestamp) {
        System.out.println("a");
        KeyValueIterator iter = this.kvStore.all();

        while (iter.hasNext()) {
            KeyValue entry = (KeyValue) iter.next();
            context.forward(entry.key, entry.value.toString());
        }

        iter.close();
        context.commit();
        
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void process(String dummy, String line) {
        System.out.println(this.processName+"\tprocessing...........");
        try {
                   this.kvStore.put(line, line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
