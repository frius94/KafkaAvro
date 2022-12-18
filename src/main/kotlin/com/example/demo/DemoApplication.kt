package com.example.demo

import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.lang.Exception
import java.util.*

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
//	produceDirectly()
}

fun produceDirectly() {
	val props = Properties()
	props.setProperty("bootstrap.servers", "127.0.0.1:29092")
	props.setProperty("acks", "1")
	props.setProperty("retries", "10")
	props.setProperty("key.serializer", StringSerializer::class.java.name)
	props.setProperty("value.serializer", KafkaAvroSerializer::class.java.name)
	props.setProperty("schema.registry.url", "http://127.0.0.1:8085")

	val kafkaProducer = KafkaProducer<String, Employee>(props)
	val topic = "employee-avro"

	val employee = Employee.newBuilder()
		.setName("Umut")
		.setAge(28)
		.build()

	val producerRecord = ProducerRecord<String, Employee>(topic, employee)
	kafkaProducer.send(producerRecord) { metadata, exception ->

		if (exception == null) {
			println("Success!")
			println(metadata.toString())
		} else {
			exception.printStackTrace()
		}
	}

	kafkaProducer.flush()
	kafkaProducer.close()
}