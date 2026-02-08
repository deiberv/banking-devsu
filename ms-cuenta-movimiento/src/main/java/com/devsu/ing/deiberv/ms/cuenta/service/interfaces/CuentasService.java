package com.devsu.ing.deiberv.ms.cuenta.service.interfaces;

import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaDto;
import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaRequest;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CuentasService {

  /**
   * Obtiene un listado de cuentas paginadas
   * @param pageable
   * @return {@link Page<CuentaDto>}
   */
  Page<CuentaDto> listarCuentas(Pageable pageable);

  /**
   * Obtiene detalle de una cuenta indicada
   * @param numeroCuenta {@link String}
   * @return {@link CuentaDto}
   */
  CuentaDto buscarCuenta(String numeroCuenta);

  /**
   * Crea una cuenta nueva en la base de datos
   * @param cuentaRequest  {@link CuentaRequest}
   * @return  {@link CuentaDto}
   */
  CuentaDto crearCuenta(CuentaRequest cuentaRequest);

  /**
   * Elimina una cuenta de manera logica
   * @param numeroCuenta
   */
  void eliminarCuenta(String numeroCuenta);


  Cuenta findByNroCuenta(String numeroCuenta);

}
