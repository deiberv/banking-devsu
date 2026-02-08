package com.devsu.ing.deiberv.ms.cuenta.events.cliente;

import com.devsu.ing.deiberv.ms.cuenta.events.AbstractEvent;
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
