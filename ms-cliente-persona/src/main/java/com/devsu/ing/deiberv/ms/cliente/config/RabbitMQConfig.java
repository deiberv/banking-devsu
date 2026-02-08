package com.devsu.ing.deiberv.ms.cliente.config;

import com.devsu.ing.deiberv.ms.cliente.config.properties.RabbitMQProperties;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

  private final RabbitMQProperties rabbitMQProp;

  @Bean
  public Queue queue(){
    return QueueBuilder.durable(rabbitMQProp.getQueueName()).build();
  }
  @Bean
  public DirectExchange exchange(){
    return new DirectExchange(rabbitMQProp.getExchangeName());
  }

  @Bean
  public Binding binding(Queue queue, DirectExchange exchange){
    return BindingBuilder.bind(queue)
      .to(exchange)
      .with(rabbitMQProp.getRoutingKey());
  }


  @Bean
  public MessageConverter jackson2JsonMessageConverter() {
    return new JacksonJsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    var rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }



}
