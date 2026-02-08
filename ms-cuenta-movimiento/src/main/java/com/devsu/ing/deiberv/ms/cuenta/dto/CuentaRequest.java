package com.devsu.ing.deiberv.ms.cuenta.dto;

import com.devsu.ing.deiberv.ms.cuenta.enums.TipoCuentaEnum;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;
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
public class CuentaRequest {

  private String numeroCuenta;
  private TipoCuentaEnum tipoCuenta;
  private BigDecimal saldoInicial;
  private TipoEstadoEnum estado;
  private ClienteDto cliente;

}
