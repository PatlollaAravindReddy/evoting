package com.example.demo;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@Component
@Service
@RequestMapping("/api/pubsub")
@Slf4j
public class VoterAppController {

    @Value("${pubsub.project-id}")
    private String projectId;

    @Value("${pubsub.topic-id2}")
    private String topicId2;

    @Value("${pubsub.subscription-id2}")
    private String subscriptionId2;

    @Value("${pubsub.topic-id}")
    private String topicId;

    @Value("${pubsub.subscription-id}")
    private String subscriptionId;

    @RequestMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestBody String sampleJsonMessage)
            throws InterruptedException, IOException, ExecutionException {
        TopicName topicName = TopicName.of(projectId, topicId2);
        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(topicName).build();
            ByteString data = ByteString.copyFromUtf8(sampleJsonMessage);
            PubsubMessage pubSubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubSubMessage);
            System.out.println("Message Id generated by pubsub {}" + " " + messageIdFuture.get());
            return new ResponseEntity<>("Message Id generated by Topic :" + messageIdFuture.get(), HttpStatus.OK);
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }

    @RequestMapping("/subscribe")
    public ResponseEntity<String> receiveMessage() {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId2);
        var wrapper = new Object() { String messageFromPubSub; };
        MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
            System.out.println("Message Id : " + message.getMessageId());
            wrapper.messageFromPubSub = message.getData().toStringUtf8();
            System.out.println("Message : {}" + wrapper.messageFromPubSub);
            consumer.ack();
        };
        Subscriber subscriber = null;
        try {
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            subscriber.startAsync().awaitRunning();
            subscriber.awaitTerminated(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            subscriber.stopAsync();
        }
        return new ResponseEntity<>(wrapper.messageFromPubSub, HttpStatus.OK);
    }

    @RequestMapping("/publishVote")
    public ResponseEntity<String> sendVote(@RequestBody String sampleJsonMessage)
            throws InterruptedException, IOException, ExecutionException {
        TopicName topicName = TopicName.of(projectId, topicId);
        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(topicName).build();
            ByteString data = ByteString.copyFromUtf8(sampleJsonMessage);
            PubsubMessage pubSubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubSubMessage);
            System.out.println("Message Id generated by pubsub {}" + " " + messageIdFuture.get());
            return new ResponseEntity<>("Message Id generated by Topic :" + messageIdFuture.get(), HttpStatus.OK);
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}