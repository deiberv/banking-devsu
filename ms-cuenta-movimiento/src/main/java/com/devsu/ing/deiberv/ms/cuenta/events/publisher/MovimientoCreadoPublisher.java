package com.devsu.ing.deiberv.ms.cuenta.events.publisher;

import com.devsu.ing.deiberv.ms.cuenta.entity.Movimiento;
import com.devsu.ing.deiberv.ms.cuenta.events.MovimientoCreadoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovimientoCreadoPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publishEvent(final Movimiento movimiento) {
    log.info("Publicando evento de movimiento creado.");
    MovimientoCreadoEvent movimientoCreadoEvent = new MovimientoCreadoEvent(this, movimiento);
    applicationEventPublisher.publishEvent(movimientoCreadoEvent);
  }

}
