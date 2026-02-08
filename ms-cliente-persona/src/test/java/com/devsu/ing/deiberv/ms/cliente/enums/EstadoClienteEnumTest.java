package com.devsu.ing.deiberv.ms.cliente.enums;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class EstadoClienteEnumTest {
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void estadoString() {
    var resultado = EstadoClienteEnum.getEstadoString(EstadoClienteEnum.TRUE);
    Assertions.assertEquals("True", resultado);
  }

  @Test
  void estadoString2() {
    var resultado = EstadoClienteEnum.getEstadoString(EstadoClienteEnum.FALSE);
    Assertions.assertEquals("False", resultado);
  }
}
