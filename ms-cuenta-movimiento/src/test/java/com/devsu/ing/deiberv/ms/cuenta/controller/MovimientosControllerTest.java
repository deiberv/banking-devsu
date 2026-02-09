package com.devsu.ing.deiberv.ms.cuenta.controller;

import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoRequest;
import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoRpt;
import com.devsu.ing.deiberv.ms.cuenta.dto.MovimientoSaldoDto;
import com.devsu.ing.deiberv.ms.cuenta.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuenta.exception.RestExceptionHandler;
import com.devsu.ing.deiberv.ms.cuenta.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuenta.service.interfaces.MovimientosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MovimientosController.class)
@Import(RestExceptionHandler.class)
public class MovimientosControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private MovimientosService movimientosService;

  @Test
  void registrarMovimiento_exitoso_debeRetornar201() throws Exception {
    MovimientoRequest req = MovimientoRequest.builder().numeroCuenta("12345").valor(new BigDecimal("50.00")).build();

    MovimientoSaldoDto dto = MovimientoSaldoDto.builder().idMovimiento("1").cuenta("12345").soldoDisponible(new BigDecimal("150.00")).build();
    when(movimientosService.registrar(any(MovimientoRequest.class))).thenReturn(dto);

    mockMvc.perform(post("/movimientos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.cuenta").value("12345"))
      .andExpect(jsonPath("$.idMovimiento").value("1"));
  }

  @Test
  void registrarMovimiento_saldoInsuficiente_debeRetornar400() throws Exception {
    MovimientoRequest req = MovimientoRequest.builder().numeroCuenta("0001").valor(new BigDecimal("-1000")) .build();

    when(movimientosService.registrar(any(MovimientoRequest.class))).thenThrow(new SimpleException(EnumError.SALDO_INSUFICIENTE, 400));

    mockMvc.perform(post("/movimientos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.code").value(EnumError.SALDO_INSUFICIENTE.getCode()));
  }

  @Test
  void reporte_exitoso_debeRetornarLista() throws Exception {
    MovimientoRpt rpt = MovimientoRpt.builder().cliente("Cliente X").numeroCuenta("ACC1").movimiento(new BigDecimal("10.00")).saldoDisponible(new BigDecimal("110.00")).fecha(LocalDate.of(2026,1,1)).build();
    when(movimientosService.reporte(10L, LocalDate.of(2026,1,1), LocalDate.of(2026,1,2))).thenReturn(List.of(rpt));

    mockMvc.perform(get("/movimientos/cliente/10").param("fechaInicial","01/01/2026").param("fechaFin","02/01/2026").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].numeroCuenta").value("ACC1"));
  }

}
