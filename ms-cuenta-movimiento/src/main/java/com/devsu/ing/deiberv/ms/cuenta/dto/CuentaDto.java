package com.devsu.ing.deiberv.ms.cuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDto {

  private Long cuentaId;
  private String cliente;
  private String numeroCuenta;
  private String tipoCuenta;
  private BigDecimal saldoInicial;
  private String estado;


}
