package com.devsu.ing.deiberv.ms.cuenta.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoCuentaEnum {
  CORRIENTE("Corriente"),
  AHORRO("Ahorro");

  private final String descripcion;
}
