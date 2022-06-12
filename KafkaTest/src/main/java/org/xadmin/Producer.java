package org.xadmin;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


// Simple Producer class for publishing messages to the kafka-broker
public class Producer {
    public static void main(String[] args) {
        System.out.println("Hello Kafka-Producer");

        // 1. set up a logger
        final Logger logger = LoggerFactory.getLogger(Producer.class);

        // 2. create constant-variables for strings
        final String bootstrapServers = "localhost:9092";

        // 3. Create the properties object for Producer
        Properties prop = new Properties();
        // bootstrap.servers
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // key.serializer
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.serializer
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 4. Create the Producer
        final KafkaProducer<String, String> producer = new KafkaProducer<>(prop);

        // 5. Create the ProducerRecord
        // ProducerRecord<String, String> record = new ProducerRecord<>("KafkaTest", "Key-1", "Value-1");

        // 6. Send Data-Asynchronous
        // producer.send(record);


        ProducerRecord<String, String> record = null;
        for (int i = 1000; i < 1005; i++) {
            record = new ProducerRecord<>("KafkaTest", "Key-" + (i + 1), "Value-" + (i + 1));
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception ex) {
                    if (ex == null) {
                        // success case
                        logger.info("\n\n Received Record Metadata." +
                                "\n Topic: " + recordMetadata.topic() +
                                ",\n Partition: " + recordMetadata.partition() +
                                ",\n Offset: " + recordMetadata.offset() +
                                ",\n @Timestamp: " + recordMetadata.timestamp() + "\n");
                    } else {
                        // when the publishable msg FAILS to get published to kafka-broker
                        logger.error("ERROR OCCURRED : ", ex);
                    }
                }
            });
        }

        // 7. Flush and close the Producer
        producer.flush();
        producer.close();
    }
}
