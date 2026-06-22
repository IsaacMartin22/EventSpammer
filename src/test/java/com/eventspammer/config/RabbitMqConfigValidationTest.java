package com.eventspammer.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RabbitMqConfigTest {

    @Test
    void validateSkipsValidationWhenRabbitMqIsDisabled() {
        RabbitMqConfig config = new RabbitMqConfig();

        config.setEnabled(false);
        config.setHost(null);
        config.setPort(0);
        config.setUsername(null);
        config.setPassword(null);
        config.setQueueName(null);

        assertDoesNotThrow(config::validate);
    }

    @Test
    void validateAllowsValidEnabledConfig() {
        RabbitMqConfig config = validEnabledConfig();

        assertDoesNotThrow(config::validate);
    }

    @Test
    void validateRejectsMissingHostWhenEnabled() {
        RabbitMqConfig config = validEnabledConfig();
        config.setHost(" ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                config::validate
        );

        assertEquals("rabbitMq.host is required when RabbitMQ is enabled.", exception.getMessage());
    }

    @Test
    void validateRejectsNonPositivePortWhenEnabled() {
        RabbitMqConfig config = validEnabledConfig();
        config.setPort(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                config::validate
        );

        assertEquals("rabbitMq.port must be > 0 when RabbitMQ is enabled.", exception.getMessage());
    }

    @Test
    void validateRejectsMissingUsernameWhenEnabled() {
        RabbitMqConfig config = validEnabledConfig();
        config.setUsername("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                config::validate
        );

        assertEquals("rabbitMq.username is required when RabbitMQ is enabled.", exception.getMessage());
    }

    @Test
    void validateRejectsNullPasswordWhenEnabled() {
        RabbitMqConfig config = validEnabledConfig();
        config.setPassword(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                config::validate
        );

        assertEquals("rabbitMq.password is required when RabbitMQ is enabled.", exception.getMessage());
    }

    @Test
    void validateRejectsMissingQueueNameWhenEnabled() {
        RabbitMqConfig config = validEnabledConfig();
        config.setQueueName(" ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                config::validate
        );

        assertEquals("rabbitMq.queueName is required when RabbitMQ is enabled.", exception.getMessage());
    }

    private RabbitMqConfig validEnabledConfig() {
        RabbitMqConfig config = new RabbitMqConfig();

        config.setEnabled(true);
        config.setHost("localhost");
        config.setPort(5672);
        config.setUsername("guest");
        config.setPassword("guest");
        config.setQueueName("event-spam-events");

        return config;
    }
}
