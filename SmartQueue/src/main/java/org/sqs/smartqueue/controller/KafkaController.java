package org.sqs.smartqueue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sqs.smartqueue.config.messages.KafkaMessages;
import org.sqs.smartqueue.service.kafka.KafkaProducerService;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {
    @Autowired
    private KafkaMessages messageProducer;

    @Value("${app.kafka.topic.business-events}")
    private String topic;

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        messageProducer.sendMessage(topic, message);
        return "Message sent: " + message;
    }
}

