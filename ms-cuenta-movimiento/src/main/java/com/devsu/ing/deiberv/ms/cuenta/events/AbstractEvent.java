package com.devsu.ing.deiberv.ms.cuenta.events;

import lombok.Getter;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
public class AbstractEvent {

  private final String idEvento;
  private final String accion;
  private final Date fechaEvento;


  protected AbstractEvent(String accion) {
    this.idEvento = UUID.randomUUID().toString();
    this.fechaEvento = Date.from(Instant.now());
    this.accion = accion;
  }



}
