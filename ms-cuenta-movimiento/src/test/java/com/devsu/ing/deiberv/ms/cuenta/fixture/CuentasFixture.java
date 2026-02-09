package com.devsu.ing.deiberv.ms.cuenta.fixture;

import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaDto;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoCuentaEnum;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

public class CuentasFixture {
  public static Page<CuentaDto> obtenerListadoCuentas() {
    return new PageImpl<>(List.of(obtenerCuentaVm()), PageRequest.of(1,20), 3);
  }

  public static Page<Cuenta> obtenerListadoCuentasEntity() {
    return new PageImpl<>(List.of(obtenerCuentaEntity()), PageRequest.of(1,20), 3);
  }

  public static List<Cuenta> getListaCuentaEntity() {
    return List.of(obtenerCuentaEntity(), obtenerCuentaEntity());
  }

  public static CuentaDto obtenerCuentaVm(){
    return CuentaDto.builder()
      .cuentaId(1L)
      .cliente("Cliente Prueba")
      .numeroCuenta("9874523")
      .tipoCuenta("Corriente")
      .saldoInicial(BigDecimal.TEN)
      .estado("True")
      .build();
  }
  public static Cuenta obtenerCuentaEntity() {
    return Cuenta.builder()
      .cuentaId(123L)
      .numeroCuenta("123456")
      .tipoCuenta(TipoCuentaEnum.CORRIENTE)
      .saldo(BigDecimal.TEN)
      .estado(TipoEstadoEnum.True)
      .build();
  }
}
