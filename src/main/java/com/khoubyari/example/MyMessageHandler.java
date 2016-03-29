package com.khoubyari.example;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by skhoubyari on 3/29/16.
 */
@Service
public class MyMessageHandler implements MessageHandler {

    public void handleMessage(org.springframework.messaging.Message<?> message) throws org.springframework.messaging.MessagingException {
        for (String headerKey : message.getHeaders().keySet()) {
            System.out.println("Header: "+headerKey+"="+message.getHeaders().get(headerKey));
        }
        System.out.println("Payload: "+message.getPayload());
    }

}
