package com.devsu.ing.deiberv.ms.cliente.service.publisher;

import com.devsu.ing.deiberv.ms.cliente.config.properties.RabbitMQProperties;
import com.devsu.ing.deiberv.ms.cliente.events.AbstractEvent;
import com.devsu.ing.deiberv.ms.cliente.events.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.cliente.events.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.cliente.events.ClienteModificadoEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@ExtendWith(OutputCaptureExtension.class)
public class ClienteEventProducerTest {

  @Mock
  private RabbitMQProperties rabbitMQProp;
  @Mock
  private RabbitTemplate rabbitTemplate;
  @InjectMocks
  private ClienteEventProducer clienteEventProducer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Mockito.when(rabbitMQProp.getExchangeName()).thenReturn("text_exchange");
    Mockito.when(rabbitMQProp.getQueueName()).thenReturn("text_quote");
    Mockito.when(rabbitMQProp.getRoutingKey()).thenReturn("test_routingKey");
  }

  @ParameterizedTest
  @MethodSource("provideClienteEvent")
  void publicarEvento(AbstractEvent event) {
    Mockito.doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), Mockito.any(Object.class));
    this.clienteEventProducer.publicarEvento(event);
    Mockito.verify(rabbitTemplate, times(1))
      .convertAndSend(anyString(), anyString(), any(Object.class));
  }

  @Test
  void publicarEventoException(CapturedOutput output){
    var evento = new ClienteModificadoEvent(123L,"Cliente Prueba");
    doThrow(new AmqpException("Error al publicar evento"))
      .when(rabbitTemplate).convertAndSend(anyString(), anyString(), Mockito.any(Object.class));
    this.clienteEventProducer.publicarEvento(evento);
    Assertions.assertTrue(output.getOut().contains("Se ha presentado un error publicando el evento"));
  }

  private static Stream<Arguments> provideClienteEvent() {
    return Stream.of(
      Arguments.of(new ClienteCreadoEvent(123L,"Cliente Prueba")),
      Arguments.of(new ClienteModificadoEvent(123L,"Cliente Prueba")),
      Arguments.of(new ClienteEliminadoEvent(123L, "Cliente Prueba"))
    );
  }


}
