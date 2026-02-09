package com.devsu.ing.deiberv.ms.cuenta.service.impl;

import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaDto;
import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaRequest;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoCuentaEnum;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;
import com.devsu.ing.deiberv.ms.cuenta.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuenta.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuenta.mapper.CuentaMapper;
import com.devsu.ing.deiberv.ms.cuenta.repository.CuentasRepository;
import com.devsu.ing.deiberv.ms.cuenta.service.interfaces.ClientesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentasServiceImplTest {

  @Mock
  private CuentasRepository cuentasRepository;

  @Mock
  private CuentaMapper cuentaMapper;

  @Mock
  private ClientesService clientesService;

  @InjectMocks
  private CuentasServiceImpl cuentasService;

  @Test
  void listar_debeRetornarPage() {
    CuentaDto dto = CuentaDto.builder().cuentaId(1L).numeroCuenta("N1").build();
    Page<CuentaDto> page = new PageImpl<>(List.of(dto));
    when(cuentasRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(mock(Cuenta.class))));
    when(cuentaMapper.toDto(any(Cuenta.class))).thenReturn(dto);

    Page<CuentaDto> resultado = cuentasService.listarCuentas(Pageable.unpaged());

    assertNotNull(resultado);
    assertTrue(resultado.hasContent());
  }

  @Test
  void buscarCuenta_existente_debeRetornarDto() {
    Cuenta cuenta = Cuenta.builder().cuentaId(1L).numeroCuenta("A1").build();
    when(cuentasRepository.findByNumeroCuenta("A1")).thenReturn(Optional.of(cuenta));
    when(cuentaMapper.toDto(cuenta)).thenReturn(CuentaDto.builder().cuentaId(1L).numeroCuenta("A1").build());

    CuentaDto dto = cuentasService.buscarCuenta("A1");

    assertNotNull(dto);
    assertEquals("A1", dto.getNumeroCuenta());
  }

  @Test
  void buscarCuenta_noExiste_debeLanzarSimpleException() {
    when(cuentasRepository.findByNumeroCuenta("X")).thenReturn(Optional.empty());

    assertThrows(SimpleException.class, () -> cuentasService.buscarCuenta("X"));
  }

  @Test
  void crearCuenta_saldoNegativo_debeLanzarSimpleException() {
    CuentaRequest req = new CuentaRequest();
    req.setNumeroCuenta("C1");
    req.setSaldoInicial(new BigDecimal("-1"));

    assertThrows(SimpleException.class, () -> cuentasService.crearCuenta(req));
  }

  @Test
  void crearCuenta_exitoso_debeGuardarYRetornarDto() {
    CuentaRequest req = new CuentaRequest();
    req.setNumeroCuenta("C2");
    req.setSaldoInicial(new BigDecimal("10"));
    req.setTipoCuenta(TipoCuentaEnum.AHORRO);
    req.setEstado(TipoEstadoEnum.True);
    Cliente cliente = Cliente.builder().clienteId(2L).nombre("Cliente").build();
    req.setCliente(com.devsu.ing.deiberv.ms.cuenta.dto.ClienteDto.builder().clienteId(2L).build());

    when(clientesService.buscarCliente(2L)).thenReturn(cliente);
    when(cuentasRepository.save(any(Cuenta.class))).thenAnswer(inv -> inv.getArgument(0));
    when(cuentaMapper.toDto(any(Cuenta.class))).thenReturn(CuentaDto.builder().cuentaId(5L).numeroCuenta("C2").build());

    CuentaDto dto = cuentasService.crearCuenta(req);

    assertNotNull(dto);
    assertEquals("C2", dto.getNumeroCuenta());
    verify(cuentasRepository, times(1)).save(any(Cuenta.class));
  }

  @Test
  void eliminarCuenta_noExiste_debeLanzarSimpleException() {
    when(cuentasRepository.findByNumeroCuenta("NO"))
      .thenReturn(Optional.empty());

    assertThrows(SimpleException.class, () -> cuentasService.eliminarCuenta("NO"));
  }

  @Test
  void eliminarCuenta_exitoso_debeCambiarEstado() {
    Cuenta cuenta = Cuenta.builder().cuentaId(9L).numeroCuenta("DEL").estado(TipoEstadoEnum.True).build();
    when(cuentasRepository.findByNumeroCuenta("DEL")).thenReturn(Optional.of(cuenta));
    when(cuentasRepository.save(any(Cuenta.class))).thenAnswer(inv -> inv.getArgument(0));

    assertDoesNotThrow(() -> cuentasService.eliminarCuenta("DEL"));
    verify(cuentasRepository, times(1)).save(any(Cuenta.class));
  }

}

