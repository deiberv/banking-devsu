package com.devsu.ing.deiberv.ms.cliente.events;

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
