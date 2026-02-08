package com.devsu.ing.deiberv.ms.cuenta.config;

import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteModificadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {


  @Bean
  public MessageConverter jackson2JsonMessageConverter() {
    var converter = new JacksonJsonMessageConverter();
    converter.setClassMapper(classMapper());
    return converter;
  }

  @Bean
  public DefaultClassMapper classMapper() {
    DefaultClassMapper classMapper = new DefaultClassMapper();
    Map<String, Class<?>> idClassMapping = new HashMap<>();

    idClassMapping.put("cliente.creado", ClienteCreadoEvent.class);
    idClassMapping.put("cliente.modificado", ClienteModificadoEvent.class);
    idClassMapping.put("cliente.eliminado", ClienteEliminadoEvent.class);

    classMapper.setIdClassMapping(idClassMapping);
    return classMapper;
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    var rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }


}
