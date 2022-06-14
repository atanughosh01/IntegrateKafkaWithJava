package org.xadmin;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;


// Simple Consumer class for receiving the published messages from the kafka-broker
public class Consumer {
    public static void main(String[] args) {
        System.out.println("Hello Kafka-Consumer");

        // 1. create logger for consumer class
        final Logger logger = LoggerFactory.getLogger(Consumer.class);

        // 2. create constant-variables for strings
        final String bootstrapServers = "localhost:9092";
        final String consumerGroupID = "java-group-consumer";

        // 3. create and populate properties object
        Properties prop = new Properties();
        // bootstrap.servers
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // key.deSerializer
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value.deSerializer
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // group_id_Config
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupID);
        // offset_rest_config
        prop.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 4. create consumer instance
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);

        // 5. subscribe to topic(s)
        consumer.subscribe(Arrays.asList("KafkaTest"));

        // 6. poll and consume records
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                logger.info("\n\n Received New Record." +
                        "\n Key: " + record.key() +
                        ",\n Value: " + record.value() +
                        ",\n Topic: " + record.topic() +
                        ",\n Partition: " + record.partition() +
                        ",\n Offset: " + record.offset() +
                        ",\n @Timestamp: " + record.timestamp() + "\n");
            }
            // consumer.close();
        }

        // 7. close the consumer
        // consumer.close();
    }
}
