package com.dietideals.dietideals24_25.utils;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SNSService {
    private AmazonSNS snsClient;
    private static final String APPLICATION_ARN = "arn:aws:sns:eu-north-1:925991388444:app/GCM/DietiDealsFCM";

    public SNSService() {
        this.snsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.EU_NORTH_1).build();
    }

    public void sendNotification(String deviceToken, String title, String body) {
        try {
            String endpointArn = getOrCreateEndpointArn(deviceToken);
            String payload = buildNotificationPayload(title, body);

            PublishRequest request = new PublishRequest()
                    .withTargetArn(endpointArn)
                    .withMessageStructure("json")
                    .withMessage(payload);

            PublishResult result = snsClient.publish(request);
            System.out.println("Messaggio inviato con successo. Message ID: " + result.getMessageId());

        } catch (Exception e) {
            System.err.println("Errore durante l'invio della notifica: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getOrCreateEndpointArn(String deviceToken) {
        try {
            CreatePlatformEndpointRequest request = new CreatePlatformEndpointRequest()
                    .withPlatformApplicationArn(APPLICATION_ARN)
                    .withToken(deviceToken);

            CreatePlatformEndpointResult result = snsClient.createPlatformEndpoint(request);

            return result.getEndpointArn();

        } catch (InvalidParameterException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("DuplicateEndpoint")) {
                String duplicateArn = extractArnFromError(errorMessage);
                return duplicateArn;
            }
            throw e;
        }
    }

    private String extractArnFromError(String errorMessage) {
        int startIndex = errorMessage.indexOf("arn:aws:sns");
        if (startIndex != -1) {
            return errorMessage.substring(startIndex).split(" ")[0];
        }
        return null;
    }

    private String buildNotificationPayload(String title, String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Costruisci la parte GCM
            Map<String, String> notification = new HashMap<>();
            notification.put("title", title);
            notification.put("body", body);

            Map<String, Object> gcm = new HashMap<>();
            gcm.put("notification", notification);

            // Costruisci il payload principale
            Map<String, Object> payload = new HashMap<>();
            payload.put("default", "Notification");
            payload.put("GCM", objectMapper.writeValueAsString(gcm));

            // Serializza l'intero payload
            return objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException("Errore nella costruzione del payload JSON", e);
        }
    }

}