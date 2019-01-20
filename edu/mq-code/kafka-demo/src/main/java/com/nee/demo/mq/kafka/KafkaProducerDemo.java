package com.nee.demo.mq.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerDemo extends Thread{

    private final KafkaProducer<Integer, String> producer;

    private final String topic;


    public KafkaProducerDemo(String topic) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.5:9092, 192.168.1.3:9092, 192.168.1.6:9092, 192.168.1.7:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducerDemo");
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }

    @Override
    public void run() {

        int num = 0;
        while (num < 50) {
            String message = "message_ " + num;
            System.out.println("begin send message: " + message);

            try {
                RecordMetadata recordMetadata = (RecordMetadata) producer.send(new ProducerRecord(topic, message)).get();
                System.out.println("async-offset: " + recordMetadata.offset() + "-> partition: " + recordMetadata.partition());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            producer.send(new ProducerRecord<>(topic, message), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (recordMetadata != null) {
                        System.out.println("async-offset: " + recordMetadata.offset() + "-> partition: " + recordMetadata.partition());
                    }
                }
            });


            num++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        new KafkaProducerDemo("test").start();
    }
}
