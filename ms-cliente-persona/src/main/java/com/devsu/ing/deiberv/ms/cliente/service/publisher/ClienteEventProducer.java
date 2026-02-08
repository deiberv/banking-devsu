package com.devsu.ing.deiberv.ms.cliente.service.publisher;

import com.devsu.ing.deiberv.ms.cliente.config.properties.RabbitMQProperties;
import com.devsu.ing.deiberv.ms.cliente.events.AbstractEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteEventProducer {

  private final RabbitMQProperties rabbitMQProp;
  private final RabbitTemplate rabbitTemplate;

  public void publicarEvento(AbstractEvent event) {
    try {
      log.info("Publicando evento -> {}",event);
      this.rabbitTemplate.convertAndSend(rabbitMQProp.getExchangeName(), rabbitMQProp.getRoutingKey(), event);
    } catch (AmqpException amqpException) {
      log.error("Se ha presentado un error publicando el evento {}, causado por {}", event, amqpException.getMessage());
    }
  }

}
