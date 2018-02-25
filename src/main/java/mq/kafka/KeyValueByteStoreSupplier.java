package mq.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreSupplier;
import org.apache.kafka.streams.state.internals.InMemoryKeyValueStore;

public class KeyValueByteStoreSupplier implements KeyValueBytesStoreSupplier{
    
    private final String name;
    

    public KeyValueByteStoreSupplier(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public KeyValueStore<Bytes, byte[]> get() {
        return new InMemoryKeyValueStore<>(name, Serdes.Bytes(), Serdes.ByteArray());
    }

    @Override
    public String metricsScope() {
        return "in-memory-state";
    }

}
