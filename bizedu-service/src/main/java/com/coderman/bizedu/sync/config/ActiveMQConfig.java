package com.coderman.bizedu.sync.config;

import com.coderman.bizedu.sync.listener.ActiveMqListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;
import javax.jms.Session;

/**
 * @author zhangyukang
 */
@Configuration
@ConfigurationProperties(prefix = "sync.activemq")
@Data
@Slf4j
public class ActiveMQConfig {

    private String queueName;

    private String brokerUrl;

    private String username;

    private String password;

    @Resource
    private ActiveMqListener activeMqListener;

    /**
     * 连接池的连接工厂，优化Mq的性能
     *
     * @param activeMqConnectionFactory
     * @return
     */
    @Bean
    public PooledConnectionFactory pooledConnectionFactory(@NonNull ActiveMQConnectionFactory activeMqConnectionFactory) {
        PooledConnectionFactory cachingConnectionFactory = new PooledConnectionFactory(activeMqConnectionFactory);
        cachingConnectionFactory.setMaxConnections(8);
        return cachingConnectionFactory;
    }

    /**
     * activeMQ连接工厂
     *
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMqConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory  = new ActiveMQConnectionFactory(username, password, brokerUrl);
        // 重试次数设置为6次
         connectionFactory.getRedeliveryPolicy().setMaximumRedeliveries(8);
         // 重试间隔
         connectionFactory.getRedeliveryPolicy().setRedeliveryDelay(5000);
         // 第一次重试之前的等待时间
         connectionFactory.getRedeliveryPolicy().setInitialRedeliveryDelay(5000);
         // 指数递增系数
        connectionFactory.getRedeliveryPolicy().setBackOffMultiplier(5.0);
        // 防止消息冲突
        connectionFactory.getRedeliveryPolicy().setUseCollisionAvoidance(true);
        // 不阻塞队列的方式
        connectionFactory.setNonBlockingRedelivery(true);
        return connectionFactory;
    }

    /**
     * 消息监听容器
     *
     * @param pooledConnectionFactory
     * @return
     */
    @Bean
    public DefaultMessageListenerContainer jmsListenerContainerFactory(PooledConnectionFactory pooledConnectionFactory) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(pooledConnectionFactory);
        container.setDestinationName(queueName);
        container.setMessageListener(activeMqListener);
        container.setConcurrentConsumers(6);
        // 这里要注意activemq和springboot整合的时候，手动提交为4才生效，和原生的不一样
        container.setSessionAcknowledgeMode(4);
        container.setSessionTransacted(false);
        container.setPubSubDomain(false);
        return container;
    }

    @Bean
    public JmsTemplate jmsTemplate(PooledConnectionFactory pooledConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(pooledConnectionFactory);
        jmsTemplate.setDefaultDestinationName(queueName);
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        jmsTemplate.setSessionTransacted(false);
        return jmsTemplate;
    }
}
