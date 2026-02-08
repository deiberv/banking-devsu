package com.devsu.ing.deiberv.ms.cuenta.mapper;

import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoSaldoDto;
import com.devsu.ing.deiberv.ms.cuenta.entity.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

  @Mapping(source = "cuenta.numeroCuenta", target = "cuenta")
  @Mapping(source = "movimientoId", target = "idMovimiento")
  @Mapping(source = "saldo", target = "soldoDisponible")
  MovimientoSaldoDto toMovimientoSaldoDto(Movimiento movimiento);

}
