package org.xadmin;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/*
 * Sample Producer class for publishing messages to the kafka-broker
 */
public class Producer {
    public static void main(String[] args) {

        // 1. Create the properties object for Producer
        Properties prop = new Properties();
        // bootstrap.servers
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // key.serializer
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.serializer
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 2. Create the Producer
        final KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        /**
          * try (final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop)) {
          *     System.out.println("Producer Has Been Created Successfully!");
          * } catch (Exception e) {
          *     e.printStackTrace();
          * }
          */

        // 3. Create the ProducerRecord
        ProducerRecord<String, String> record = new ProducerRecord<>("KafkaTest", "Key-1", "Value-1");

        // 4. Send Data-Asynchronous
        producer.send(record);

        /**
          * final int itrLim = 100;
          * for (int i=0; i<itrLim; i++) {
          *     record = new ProducerRecord<>("KafkaTest", "Key-"+(i+1), "Value-"+(i+1));
          *     producer.send(record);
          * }
          */

        // 5. Flush and close the Producer
        producer.flush();
        producer.close();
    }
}
