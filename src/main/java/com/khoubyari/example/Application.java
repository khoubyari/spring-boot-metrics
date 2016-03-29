package com.khoubyari.example;


import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MessageChannelMetricWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private static final Class<Application> applicationClass = Application.class;

    @Autowired
    MyMessageHandler myMessageHadler;

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    @Bean
    public MessageChannel metricsChannel() {
        return new DirectChannel();
    }

    @Bean
    @ExportMetricWriter
    public MessageChannelMetricWriter messageChannelMetricWriter() {
        return new MessageChannelMetricWriter(metricsChannel());
    }

//    @Bean
//    @ServiceActivator(inputChannel = "metricsChannel")
//    public MessageHandler metricsHandler() {
//        return System.out::println;
//    }

    @Bean
    @ServiceActivator(inputChannel = "metricsChannel")
    public MessageHandler metricsHandler() {
        return myMessageHadler;
    }

//    @ServiceActivator(inputChannel = "metricsChannel")
//    public MessageHandler handleMessage(org.springframework.messaging.Message<?> message) {
//        return new MessageHandler() {
//            @Override
//            public void handleMessage(Message<?> message) throws MessagingException {
//                System.out.println("Message [" + message.toString() + "] is received...");
//            }
//        };
//    }
}
