package com.devsu.ing.deiberv.ms.cliente.dtos;

import com.devsu.ing.deiberv.ms.cliente.enums.EstadoClienteEnum;
import com.devsu.ing.deiberv.ms.cliente.enums.GeneroEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

  private String nombre;
  private GeneroEnum genero;
  private Long edad;
  private String identificacion;
  private String direccion;
  private String telefono;
  private String password;
  private EstadoClienteEnum estado;

}
