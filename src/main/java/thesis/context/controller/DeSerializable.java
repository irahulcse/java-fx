package thesis.context.controller;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import thesis.context.data.PointCloud;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class DeSerializable implements Deserializer<PointCloud> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration is needed
    }

    @Override
    public PointCloud deserialize(String topic, byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (PointCloud) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException("Error deserializing LidarData", e);
        }
    }

    @Override
    public void close() {
        // No resources to release
    }
}