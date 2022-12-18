package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class KafkaProducer {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Employee>

    @GetMapping("send")
    fun produce() {
        kafkaTemplate.send("Employee", Employee("Umut Savas", 28))
    }

}