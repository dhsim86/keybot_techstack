package com.dongho.keybot.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("!test")
public class H2Config {

    private org.h2.tools.Server webServer;
    private org.h2.tools.Server server;

    // Use this configuration with Spring WebFlux without spring-boot-start-web,
    // since H2 In-Memory Database console requires servlet api
    // https://github.com/spring-projects/spring-boot/issues/12603
    @SneakyThrows
    @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
    public void start() {
        this.webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
        this.server = org.h2.tools.Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    @EventListener(org.springframework.context.event.ContextClosedEvent.class)
    public void stop() {
        this.webServer.stop();
        this.server.stop();
    }

}
