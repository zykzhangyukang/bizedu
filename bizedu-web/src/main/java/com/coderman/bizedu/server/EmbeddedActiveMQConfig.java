package com.coderman.bizedu.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * @author 嵌入式activeMQ配置
 */
@Profile("dev")
@ImportResource("classpath:embedded-activemq.xml")
@Configuration
public class EmbeddedActiveMQConfig {
}