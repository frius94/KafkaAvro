package com.example.demo

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    @KafkaListener(topics = ["Employee"], groupId = "foo", containerFactory = "kafkaListenerContainerFactory")
    fun listenGroupFoo(message: Employee) {
        println("Received Message in group foo: $message")
    }
}