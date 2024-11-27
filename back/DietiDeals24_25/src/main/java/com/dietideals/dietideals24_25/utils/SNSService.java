package com.dietideals.dietideals24_25.utils;

import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

@Service
public class SNSService {
    private AmazonSNS snsClient;

    public SNSService() {
        this.snsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.EU_NORTH_1).build();
    }

    public void sendNotification(String message, String deviceToken) {
        try {
            PublishRequest request = new PublishRequest().withTargetArn(deviceToken).withMessage(message);
            snsClient.publish(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}