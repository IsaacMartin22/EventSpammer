package com.eventspammer.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RabbitMqConfig {

    private boolean enabled = false;
    private String host = "localhost";
    private int port = 5672;
    private String username = "guest";
    private String password = "guest";
    private String queueName = "event-spam-events";

    public void validate() {
        if (!enabled) {
            return;
        }

        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("rabbitMq.host is required when RabbitMQ is enabled.");
        }

        if (port <= 0) {
            throw new IllegalArgumentException("rabbitMq.port must be > 0 when RabbitMQ is enabled.");
        }

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("rabbitMq.username is required when RabbitMQ is enabled.");
        }

        if (password == null) {
            throw new IllegalArgumentException("rabbitMq.password is required when RabbitMQ is enabled.");
        }

        if (queueName == null || queueName.isBlank()) {
            throw new IllegalArgumentException("rabbitMq.queueName is required when RabbitMQ is enabled.");
        }
    }
}
