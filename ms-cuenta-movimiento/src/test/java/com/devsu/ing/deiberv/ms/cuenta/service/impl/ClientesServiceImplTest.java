package com.devsu.ing.deiberv.ms.cuenta.service.impl;

import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuenta.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuenta.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuenta.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientesServiceImplTest {

  @Mock
  private ClienteRepository clienteRepository;

  @InjectMocks
  private ClientesServiceImpl clientesService;

  @Test
  void buscarCliente_cuandoExiste_debeRetornarCliente() {
    Cliente cliente = Cliente.builder().clienteId(1L).nombre("Test").build();
    when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

    Cliente resultado = clientesService.buscarCliente(1L);

    assertNotNull(resultado);
    assertEquals(1L, resultado.getClienteId());
    assertEquals("Test", resultado.getNombre());
  }

  @Test
  void buscarCliente_cuandoNoExiste_debeLanzarSimpleException() {
    when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

    SimpleException ex = assertThrows(SimpleException.class, () -> clientesService.buscarCliente(99L));
    assertEquals(EnumError.CREAR_CUENTA_CLIENTE_NOT_FOUND, ex.getErrorEnum());
  }

}

