package com.devsu.ing.deiberv.ms.cliente.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

  private Long clienteId;
  private String nombre;
  private String genero;
  private Long edad;
  private String identificacion;
  private String direccion;
  private String telefono;
  private String estado;

}
