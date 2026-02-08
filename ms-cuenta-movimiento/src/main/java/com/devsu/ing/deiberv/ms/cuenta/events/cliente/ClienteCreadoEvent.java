package com.devsu.ing.deiberv.ms.cuenta.events.cliente;

import com.devsu.ing.deiberv.ms.cuenta.events.AbstractEvent;
import lombok.Getter;

@Getter
public class ClienteCreadoEvent extends AbstractEvent {

  private Long clienteId;
  private String nombre;

  public ClienteCreadoEvent(Long clienteId, String nombre) {
    super("CLIENTE_CREADO");
    this.clienteId = clienteId;
    this.nombre = nombre;
  }


}
