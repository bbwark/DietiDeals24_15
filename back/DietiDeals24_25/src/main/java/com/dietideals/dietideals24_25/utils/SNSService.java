package com.dietideals.dietideals24_25.utils;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Service
public class SNSService {
    private AmazonSNS snsClient;

    public SNSService() {
        this.snsClient = AmazonSNSClientBuilder.standard().withRegion("eu-north-1").build();
    }

    public void sendNotification(String message, String deviceToken) {
        try {
            PublishRequest request = new PublishRequest().withTargetArn(deviceToken).withMessage(message);
            PublishResult result = snsClient.publish(request);

            System.out.println("Notification sent. MessageId: " + result.getMessageId());
        } catch (Exception e) {
            System.out.println("Error sending notification: " + e.getMessage());
        }
    }

}