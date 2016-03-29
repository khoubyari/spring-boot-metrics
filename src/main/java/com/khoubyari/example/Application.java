package com.khoubyari.example;


import ch.qos.logback.classic.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MessageChannelMetricWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@EnableAutoConfiguration  // Sprint Boot Auto Configuration
@ComponentScan(basePackages = "com.khoubyari.example")
public class Application extends SpringBootServletInitializer {

    private static final Class<Application> applicationClass = Application.class;

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

    @Bean
    @ServiceActivator(inputChannel = "metricsChannel")
    public MessageHandler metricsHandler() {
        return System.out::println;
    }

}
