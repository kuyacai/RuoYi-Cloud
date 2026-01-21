package com.ruoyi.product.rq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitAgentConfig {

    @Value("${spring.rabbitmq.agent.exchange:agent.workflow.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.agent.queue:agent.task.python.queue}")
    private String queueName;

    @Value("${spring.rabbitmq.agent.routing-key:agent.task.python.key}")
    private String routingKey;

    @Bean
    public DirectExchange agentExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue agentQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding bindingAgent() {
        return BindingBuilder.bind(agentQueue()).to(agentExchange()).with(routingKey);
    }

    /**
     * 使用 JSON 序列化器，发送的消息会自动转为 JSON 字符串
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}