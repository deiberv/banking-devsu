package com.devsu.ing.deiberv.ms.cuenta.controller;

import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaDto;
import com.devsu.ing.deiberv.ms.cuenta.dto.CuentaRequest;
import com.devsu.ing.deiberv.ms.cuenta.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuenta.exception.RestExceptionHandler;
import com.devsu.ing.deiberv.ms.cuenta.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuenta.service.interfaces.CuentasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CuentasController.class)
@Import(RestExceptionHandler.class)
class CuentasControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private CuentasService cuentasService;

  @Test
  void listar_conContenido_debeRetornar200() throws Exception {
    CuentaDto dto = CuentaDto.builder().cuentaId(1L).cliente("Cliente A").numeroCuenta("0001").saldoInicial(new BigDecimal("100.00")).estado("True").tipoCuenta("AHORROS").build();
    Page<CuentaDto> page = new PageImpl<>(List.of(dto));

    when(cuentasService.listarCuentas(any(Pageable.class))).thenReturn(page);

    mockMvc.perform(get("/cuentas").param("page","0").param("size","10").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.content[0].numeroCuenta").value("0001"));
  }

  @Test
  void listar_vacio_debeRetornar204() throws Exception {
    Page<CuentaDto> empty = Page.empty();
    when(cuentasService.listarCuentas(any(Pageable.class))).thenReturn(empty);

    mockMvc.perform(get("/cuentas").param("page","0").param("size","10").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());
  }

  @Test
  void buscarPorId_existente_debeRetornar200() throws Exception {
    CuentaDto dto = CuentaDto.builder().cuentaId(2L).cliente("Cliente B").numeroCuenta("ABC123").saldoInicial(new BigDecimal("50.00")).estado("True").tipoCuenta("CORRIENTE").build();
    when(cuentasService.buscarCuenta("ABC123")).thenReturn(dto);

    mockMvc.perform(get("/cuentas/detalle/ABC123").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.numeroCuenta").value("ABC123"));
  }

  @Test
  void buscarPorId_noExiste_debeRetornar404() throws Exception {
    when(cuentasService.buscarCuenta("NOPE")).thenThrow(new SimpleException(EnumError.CUENTA_NOT_FOUND, 404));

    mockMvc.perform(get("/cuentas/detalle/NOPE").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.code").value(EnumError.CUENTA_NOT_FOUND.getCode()));
  }

  @Test
  void crear_valido_debeRetornar201() throws Exception {
    CuentaRequest req = new CuentaRequest();
    req.setNumeroCuenta("NEW01");
    req.setTipoCuenta(null);
    req.setSaldoInicial(new BigDecimal("10.00"));
    req.setEstado(null);
    req.setCliente(null);

    CuentaDto dto = CuentaDto.builder().cuentaId(3L).cliente("Cliente C").numeroCuenta("NEW01").saldoInicial(new BigDecimal("10.00")).estado("True").tipoCuenta("AHORROS").build();
    when(cuentasService.crearCuenta(any(CuentaRequest.class))).thenReturn(dto);

    mockMvc.perform(post("/cuentas").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.numeroCuenta").value("NEW01"));
  }

  @Test
  void crear_duplicada_debeRetornar400() throws Exception {
    CuentaRequest req = new CuentaRequest();
    req.setNumeroCuenta("DUP01");
    req.setSaldoInicial(new BigDecimal("0.00"));

    when(cuentasService.crearCuenta(any(CuentaRequest.class))).thenThrow(new SimpleException(EnumError.CREAR_CUENTA_EXISTENTE, 400));

    mockMvc.perform(post("/cuentas").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.code").value(EnumError.CREAR_CUENTA_EXISTENTE.getCode()));
  }

  @Test
  void eliminar_exito_debeRetornar200() throws Exception {
    doNothing().when(cuentasService).eliminarCuenta("DEL01");

    mockMvc.perform(delete("/cuentas/DEL01"))
      .andExpect(status().isOk());
  }

  @Test
  void eliminar_noExiste_debeRetornar404() throws Exception {
    doThrow(new SimpleException(EnumError.CUENTA_NOT_FOUND, 404)).when(cuentasService).eliminarCuenta("MISSING");

    mockMvc.perform(delete("/cuentas/MISSING"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.code").value(EnumError.CUENTA_NOT_FOUND.getCode()));
  }

}
