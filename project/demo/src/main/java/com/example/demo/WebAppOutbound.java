package com.example.demo;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;


@Configuration
@RequiredArgsConstructor
public class WebAppOutbound {
    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "projects/aravindblog/topics/evoting");
    }
    @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
    public interface PubsubOutboundGateway {
        void sendToPubsub(String text);
    }

    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputResultChannel")
    public MessageHandler resultSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "projects/aravindblog/topics/Results");
    }
    @MessagingGateway(defaultRequestChannel = "pubsubOutputResultChannel")
    public interface PubsubOutboundResultGateway {
        void sendToPubsub(String text);
    }
}