package com.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

public class KafkaConsumerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.17.0.2:9092");
        props.put("group.id", "1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("test"));
        while(true){
            ConsumerRecords<String, String> records=consumer.poll(Duration.ofMillis(1000L));
            for (Iterator<ConsumerRecord<String, String>> iter = records.iterator();iter.hasNext();){
                ConsumerRecord<String, String> record = iter.next();
                System.out.println("topic: "+record.topic()+" ,partition: "+record.partition()
                        +" ,offset: "+record.offset()+" ,key: "+record.key()+" ,value: "+record.value());
            }
        }
    }
}
