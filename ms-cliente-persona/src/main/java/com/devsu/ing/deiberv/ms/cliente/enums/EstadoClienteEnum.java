package com.devsu.ing.deiberv.ms.cliente.enums;

import lombok.Getter;

@Getter
public enum EstadoClienteEnum {
  TRUE,
  FALSE;

  public static String getEstadoString(EstadoClienteEnum estado) {
    return TRUE.equals(estado) ? "True" : "False";
  }
}
