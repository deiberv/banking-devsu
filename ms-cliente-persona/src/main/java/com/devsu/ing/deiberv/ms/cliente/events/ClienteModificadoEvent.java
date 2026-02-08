package com.devsu.ing.deiberv.ms.cliente.events;

import lombok.Getter;

@Getter
public class ClienteModificadoEvent extends AbstractEvent {

  private Long clienteId;
  private String nombre;

  public ClienteModificadoEvent(Long clienteId, String nombre) {
    super("CLIENTE_MODIFICADO");
    this.clienteId = clienteId;
    this.nombre = nombre;
  }

}
