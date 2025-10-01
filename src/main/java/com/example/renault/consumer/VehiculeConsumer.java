package com.example.renault.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VehiculeConsumer {

    private static final Logger log = LoggerFactory.getLogger(VehiculeConsumer.class);

    @KafkaListener(topics = "vehicule-created", groupId = "vehicule-service")
    public void consume(String message) {
        log.info("Vehicule created: " + message);
    }
}
