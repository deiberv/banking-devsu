package com.devsu.ing.deiberv.ms.cliente.events;

import lombok.Getter;

@Getter
public class ClienteEliminadoEvent extends AbstractEvent {

  private Long clienteId;
  private String nombre;
  public ClienteEliminadoEvent(Long clienteId, String nombre) {
    super("CLIENTE_ELIMINADO");
    this.clienteId = clienteId;
    this.nombre = nombre;
  }

}
