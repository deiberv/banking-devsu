package com.devsu.ing.deiberv.ms.cuenta.service.impl;

import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaDto;
import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaRequest;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;
import com.devsu.ing.deiberv.ms.cuenta.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuenta.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuenta.mapper.CuentaMapper;
import com.devsu.ing.deiberv.ms.cuenta.repository.CuentasRepository;
import com.devsu.ing.deiberv.ms.cuenta.service.interfaces.ClientesService;
import com.devsu.ing.deiberv.ms.cuenta.service.interfaces.CuentasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuentasServiceImpl implements CuentasService {

  private final CuentasRepository cuentasRepository;
  private final CuentaMapper cuentaMapper;
  private final ClientesService clientesService;


  @Override
  public Page<CuentaDto> listarCuentas(Pageable pageable) {
    return this.cuentasRepository.findAll(pageable).map(cuentaMapper::toDto);
  }

  @Override
  public CuentaDto buscarCuenta(String numeroCuenta) {
    var cuentaEntity = this.findByNroCuenta(numeroCuenta);
    return this.cuentaMapper.toDto(cuentaEntity);
  }

  @Override
  @Transactional()
  public CuentaDto crearCuenta(CuentaRequest cuentaRequest) {
    if (cuentaRequest.getSaldoInicial().compareTo(BigDecimal.ZERO) < 0) {
      throw new SimpleException(EnumError.CREAR_CUENTA_SALDO_INVALIDO, HttpStatus.BAD_REQUEST.value());
    }

    var cliente = clientesService.buscarCliente(cuentaRequest.getCliente().getClienteId());
    var cuentaEntity = Cuenta.builder()
      .cliente(cliente)
      .numeroCuenta(cuentaRequest.getNumeroCuenta())
      .tipoCuenta(cuentaRequest.getTipoCuenta())
      .saldo(cuentaRequest.getSaldoInicial())
      .estado(cuentaRequest.getEstado())
      .build();
    this.cuentasRepository.save(cuentaEntity);
    return this.cuentaMapper.toDto(cuentaEntity);
  }

  @Override
  @Transactional()
  public void eliminarCuenta(String numeroCuenta) {
    var cuentaEntity = this.findByNroCuenta(numeroCuenta);
    cuentaEntity.setEstado(TipoEstadoEnum.False);
    this.cuentasRepository.save(cuentaEntity);
  }

  @Override
  public Cuenta findByNroCuenta(String numeroCuenta) {
    return this.cuentasRepository.findByNumeroCuenta(numeroCuenta)
      .orElseThrow(() ->new SimpleException(EnumError.CUENTA_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
  }
}
