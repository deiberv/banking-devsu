package com.devsu.ing.deiberv.ms.cliente.entity;

import com.devsu.ing.deiberv.ms.cliente.enums.GeneroEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Persona {

  private String nombre;
  @Enumerated(EnumType.STRING)
  private GeneroEnum genero;
  private Long edad;
  private String identificacion;
  private String direccion;
  private String telefono;

}
