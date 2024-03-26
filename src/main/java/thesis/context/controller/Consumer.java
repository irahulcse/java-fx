package thesis.context.controller;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import thesis.context.data.Point3D;
import thesis.context.data.PointCloud;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


public class Consumer  {
    //logic to consume the kafka
    // all the method for the data will be utilised here

    public KafkaConsumer<String, PointCloud> createConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.221.213:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "lidar-consumer-group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DeSerializable.class.getName()); // Use custom deserializer
        return new KafkaConsumer<>(properties);
    }

    public PointCloud  consumeLidarData() {


        KafkaConsumer<String, PointCloud> consumer = createConsumer();
        consumer.subscribe(Collections.singletonList("lidar4"));

        try {
            while (true) {
                ConsumerRecords<String, PointCloud> records = consumer.poll(Duration.ofMillis(2000));
                if (!records.isEmpty()) {
                    for (ConsumerRecord<String, PointCloud> record : records) {
                        PointCloud lidarData = record.value();
                        for (Point3D point : lidarData.getPoints()) {
                            System.out.println(point.getX());
                        }
                        System.out.println(lidarData.getPoints());
                        return lidarData;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
        System.out.println("lidarData received");
        return null;
    }

    public static void main(String[] args) {
        Consumer consumer= new Consumer();
        consumer.consumeLidarData();
    }
}
