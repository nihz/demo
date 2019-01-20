package com.nee.demo.mq.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerDemo extends Thread {

    private final KafkaConsumer consumer;

    public KafkaConsumerDemo(String topic) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.5:9092, 192.168.1.3:9092, 192.168.1.6:9092, 192.168.1.7:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaConsumerDemo1");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer(properties);
        consumer.subscribe(Collections.singletonList(topic));
    }

    private static void accept(ConsumerRecord<Integer, String> record) {
        System.out.println(record.value());
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Integer, String > consumerRecords = consumer.poll(1000);
            consumerRecords.forEach(KafkaConsumerDemo::accept);
        }
    }

    public static void main(String[] args) {
        new KafkaConsumerDemo("test").start();
    }
}
