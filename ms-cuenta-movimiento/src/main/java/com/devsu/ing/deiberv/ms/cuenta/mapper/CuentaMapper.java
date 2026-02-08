package com.devsu.ing.deiberv.ms.cuenta.mapper;

import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaDto;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoCuentaEnum;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CuentaMapper {


  @Mapping(source = "saldo", target = "saldoInicial")
  @Mapping(source = "cliente.nombre", target = "cliente")
  @Mapping(source = "tipoCuenta", target = "tipoCuenta", qualifiedByName = "tipoCuentaToString")
  @Mapping(source = "estado", target = "estado", qualifiedByName = "estadoToString")
  CuentaDto toDto(Cuenta cuenta);

  @Named("tipoCuentaToString")
  default String tipoCuentaToString(TipoCuentaEnum tipoCuenta) {
    return tipoCuenta == null ? null : tipoCuenta.getDescripcion();
  }

  @Named("estadoToString")
  default String estadoToString(TipoEstadoEnum tipoEstado) {
    return tipoEstado == null ? null : tipoEstado.getDescripcion();
  }

}
