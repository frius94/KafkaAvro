package com.example.demo.config

import com.example.demo.Employee
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory


@EnableKafka
@Configuration
class KafkaConsumerConfig {
    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val kafkaAvroDeserializer = KafkaAvroDeserializer()
        kafkaAvroDeserializer.configure(consumerConfigs(), false)
        return DefaultKafkaConsumerFactory(consumerConfigs(), StringDeserializer(), kafkaAvroDeserializer)
    }

    @Bean
    fun consumerConfigs(): HashMap<String, Any> {
        val props: HashMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "127.0.0.1:29092"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "foo"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        props[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://127.0.0.1:8085"
        props[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = "true"
        return props
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Employee>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Employee>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}