package com.covid19.match.amazon.queue;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.covid19.match.amazon.enums.QueueType;
import com.covid19.match.amazon.enums.Queues;
import com.covid19.match.amazon.enums.Regions;
import com.covid19.match.amazon.models.BounceMessage;
import com.covid19.match.amazon.models.ComplaintMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Component
@Slf4j
public class ReadAmazonQueue implements SmartInitializingSingleton {
    private AmazonSQS amazonSQS;
    private Regions region;
    private Gson gson;

    @Autowired
    ReadAmazonQueue(AmazonSQS amazonSQS, Regions region, Gson gson) {
        this.amazonSQS = amazonSQS;
        this.region = region;
        this.gson = gson;
    }

    @Override
    public void afterSingletonsInstantiated() {
        CompletableFuture.runAsync(this::readAmazonBuncesQueue);
        CompletableFuture.runAsync(this::readAmazonComplainsQueue);
    }

    public void readAmazonBuncesQueue() {
        String queueUrl = getQueueUrl(QueueType.BOUNCE);

        while (true) {
            log.info("Receiving messages from queue {}", QueueType.BOUNCE);
            final ReceiveMessageRequest receiveMessageRequest =
                    new ReceiveMessageRequest(queueUrl)
                            .withMaxNumberOfMessages(1)
                            .withWaitTimeSeconds(3);
            final List<Message> messages =
                    amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

            messages.stream().forEach(message -> {
                try {
                    BounceMessage bounceMessage = gson.fromJson(message.getBody(), BounceMessage.class);

                } catch (Exception ex) {
                    log.error("Read message from queue {}, region {}, exception {}", QueueType.BOUNCE.name(), region, ex);
                    return;
                }

                deleteMessage(queueUrl, message);
            });
        }
    }

    public void readAmazonComplainsQueue() {
        String queueUrl = getQueueUrl(QueueType.COMPLAIN);

        while (true) {
            log.info("Receiving messages from queue {}", QueueType.COMPLAIN);
            final ReceiveMessageRequest receiveMessageRequest =
                    new ReceiveMessageRequest(queueUrl)
                            .withMaxNumberOfMessages(1)
                            .withWaitTimeSeconds(3);
            final List<Message> messages =
                    amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

            messages.stream().forEach(message -> {
                try {
                    ComplaintMessage complaintMessage = gson.fromJson(message.getBody(), ComplaintMessage.class);
                    System.out.println(complaintMessage);
                } catch (Exception ex) {
                    log.error("Read message from queue {}, region {}, exception {}", QueueType.COMPLAIN.name(), region, ex);
                    return;
                }

                deleteMessage(queueUrl, message);
            });
        }
    }

    private String getQueueUrl(QueueType queueType) {
        String queueName = Stream.of(Queues.values())
                .filter(q -> q.getRegion().equals(region) && q.getQueueType().equals(queueType))
                .map(Queues::getName)
                .findFirst()
                .orElse(null);
        return amazonSQS.getQueueUrl(queueName).getQueueUrl();
    }

    private void deleteMessage(String queueUrl, Message message) {

        final String messageReceiptHandle = message.getReceiptHandle();
        amazonSQS.deleteMessage(new DeleteMessageRequest(queueUrl, messageReceiptHandle));
    }


}
