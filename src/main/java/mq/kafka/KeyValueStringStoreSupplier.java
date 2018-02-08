package mq.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreSupplier;
import org.apache.kafka.streams.state.internals.InMemoryKeyValueStore;

public class KeyValueStringStoreSupplier implements StoreSupplier<KeyValueStore<String, String>>{
    
    private final String name;
    

    public KeyValueStringStoreSupplier(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public KeyValueStore<String, String> get() {
        return new InMemoryKeyValueStore<>(name, Serdes.String(), Serdes.String());
    }

    @Override
    public String metricsScope() {
        return "in-memory-state";
    }

}
