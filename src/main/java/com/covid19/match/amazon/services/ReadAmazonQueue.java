package com.covid19.match.amazon.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.covid19.match.amazon.enums.QueueType;
import com.covid19.match.amazon.enums.Queues;
import com.covid19.match.amazon.enums.Regions;
import com.covid19.match.amazon.models.Bounce;
import com.covid19.match.amazon.models.BounceMessage;
import com.covid19.match.amazon.models.Complaint;
import com.covid19.match.amazon.models.ComplaintMessage;
import com.covid19.match.events.EmailNotificationEvent;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class ReadAmazonQueue {
    private AmazonSQS amazonSQS;
    private Regions region;
    private Gson gson;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ReadAmazonQueue(AmazonSQS amazonSQS, Regions region, Gson gson, ApplicationEventPublisher applicationEventPublisher) {
        this.amazonSQS = amazonSQS;
        this.region = region;
        this.gson = gson;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void readAmazonBuncesQueue() {
        String queueUrl = getQueueUrl(QueueType.BOUNCE);

        log.info("Receiving messages from queue {}", QueueType.BOUNCE);
        List<Message> messages = getMessagesFromQueue(queueUrl);

        messages.forEach(message -> {
            try {
                BounceMessage bounceMessage = gson.fromJson(message.getBody(), BounceMessage.class);
                setBounceNotifications(bounceMessage);
            } catch (Exception ex) {
                log.error("Read message from queue {}, region {}, exception {}", QueueType.BOUNCE.name(), region, ex);
                return;
            }

            deleteMessage(queueUrl, message);
        });
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void readAmazonComplainsQueue() {
        String queueUrl = getQueueUrl(QueueType.COMPLAIN);

        log.info("Receiving messages from queue {}", QueueType.COMPLAIN);
        List<Message> messages = getMessagesFromQueue(queueUrl);

        messages.forEach(message -> {
            try {
                ComplaintMessage complaintMessage = gson.fromJson(message.getBody(), ComplaintMessage.class);
                sendComplaintNotifications(complaintMessage);
            } catch (Exception ex) {
                log.error("Read message from queue {}, region {}, exception {}", QueueType.COMPLAIN.name(), region, ex);
                return;
            }

            deleteMessage(queueUrl, message);
        });
    }

    private void setBounceNotifications(BounceMessage bounceMessage) {
        Optional.of(bounceMessage)
                .map(BounceMessage::getBounce)
                .map(Bounce::getBouncedRecipients).stream()
                .flatMap(Arrays::stream)
                .forEach(emailAddress -> {
                    applicationEventPublisher.publishEvent(new EmailNotificationEvent(this, emailAddress.getEmailAddress(), bounceMessage.getBounce().getBounceType()));
                });
    }

    private void sendComplaintNotifications(ComplaintMessage complaintMessage) {
        Optional.of(complaintMessage)
                .map(ComplaintMessage::getComplaint)
                .map(Complaint::getComplainedRecipients).stream()
                .flatMap(Arrays::stream)
                .forEach(complaint ->
                        applicationEventPublisher.publishEvent(new EmailNotificationEvent(this, complaint.getEmailAddress(), complaintMessage.getNotificationType()))

                );
    }

    private List<Message> getMessagesFromQueue(String queueUrl) {
        final ReceiveMessageRequest receiveMessageRequest =
                new ReceiveMessageRequest(queueUrl)
                        .withWaitTimeSeconds(3);
        return amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
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
