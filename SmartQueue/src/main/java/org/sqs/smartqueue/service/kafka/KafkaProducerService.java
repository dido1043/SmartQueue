package org.sqs.smartqueue.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.sqs.smartqueue.data.dto.NotificationDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private static final String TOPIC = "user-topic";

    @Autowired
    private KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public void sendMessage(NotificationDto notificationDto) {
        kafkaTemplate.send(TOPIC, notificationDto.getBooking(), notificationDto);
        System.out.println("Sent: " + notificationDto.getBooking());
    }
}

