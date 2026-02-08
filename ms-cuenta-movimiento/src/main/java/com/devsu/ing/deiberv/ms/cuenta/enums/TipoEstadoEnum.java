package com.devsu.ing.deiberv.ms.cuenta.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEstadoEnum {

  True("True"),
  False("False");

  private final String descripcion;

  public boolean toBooleanValue(){
    return True.descripcion.equals(this.getDescripcion());
  }


}
