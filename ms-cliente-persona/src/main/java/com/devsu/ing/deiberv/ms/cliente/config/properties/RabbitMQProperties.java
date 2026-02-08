package com.devsu.ing.deiberv.ms.cliente.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {

  private String queueName;
  private String exchangeName;
  private String routingKey;

}
