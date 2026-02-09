package com.devsu.ing.deiberv.ms.cuenta.events.listeners;

import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;
import com.devsu.ing.deiberv.ms.cuenta.events.AbstractEvent;
import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteModificadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cuenta.repository.CuentasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteIntegracionRabbitListenerTest {

  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private CuentasRepository cuentasRepository;

  @InjectMocks
  private ClienteIntegracionRabbitListener listener;

  @Captor
  private ArgumentCaptor<Cliente> clienteCaptor;

  @Captor
  private ArgumentCaptor<List<Cuenta>> cuentasCaptor;

  @BeforeEach
  void setUp() {
    // MockitoExtension se encarga del init
  }

  @Test
  void procesarEvento_clienteCreado_debeGuardarCliente() {
    ClienteCreadoEvent event = new ClienteCreadoEvent(10L, "Juan");

    when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

    assertDoesNotThrow(() -> listener.procesarEvento(event));

    verify(clienteRepository, times(1)).save(clienteCaptor.capture());
    Cliente saved = clienteCaptor.getValue();
    assertEquals(10L, saved.getClienteId());
    assertEquals("Juan", saved.getNombre());
  }

  @Test
  void procesarEvento_clienteCreado_saveLanzaException_noPropaga() {
    ClienteCreadoEvent event = new ClienteCreadoEvent(11L, "Ana");

    when(clienteRepository.save(any())).thenThrow(new RuntimeException("DB error"));

    // No debe lanzar excepcion
    assertDoesNotThrow(() -> listener.procesarEvento(event));

    verify(clienteRepository, times(1)).save(any(Cliente.class));
  }

  @Test
  void procesarEvento_clienteModificado_debeGuardarCliente() {
    ClienteModificadoEvent event = new ClienteModificadoEvent(20L, "Pedro");

    when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

    assertDoesNotThrow(() -> listener.procesarEvento(event));

    verify(clienteRepository, times(1)).save(clienteCaptor.capture());
    Cliente saved = clienteCaptor.getValue();
    assertEquals(20L, saved.getClienteId());
    assertEquals("Pedro", saved.getNombre());
  }

  @Test
  void procesarEvento_clienteModificado_saveLanzaException_noPropaga() {
    ClienteModificadoEvent event = new ClienteModificadoEvent(21L, "Luisa");

    when(clienteRepository.save(any())).thenThrow(new RuntimeException("DB error"));

    assertDoesNotThrow(() -> listener.procesarEvento(event));

    verify(clienteRepository, times(1)).save(any(Cliente.class));
  }

  @Test
  void procesarEvento_clienteEliminado_cuentasActualizadas() {
    Cliente cliente = Cliente.builder().clienteId(30L).nombre("Carlos").build();
    ClienteEliminadoEvent event = new ClienteEliminadoEvent(30L, "Carlos");

    Cuenta c1 = Cuenta.builder().cuentaId(1L).numeroCuenta("0001").saldo(new BigDecimal("100.00")).estado(TipoEstadoEnum.True).cliente(cliente).build();
    Cuenta c2 = Cuenta.builder().cuentaId(2L).numeroCuenta("0002").saldo(new BigDecimal("200.00")).estado(TipoEstadoEnum.True).cliente(cliente).build();

    when(clienteRepository.findById(30L)).thenReturn(Optional.of(cliente));
    when(cuentasRepository.findByCliente(cliente)).thenReturn(List.of(c1, c2));
    when(cuentasRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

    assertDoesNotThrow(() -> listener.procesarEvento(event));

    verify(clienteRepository, times(1)).findById(30L);
    verify(cuentasRepository, times(1)).findByCliente(cliente);
    verify(cuentasRepository, times(1)).saveAll(cuentasCaptor.capture());

    List<Cuenta> saved = cuentasCaptor.getValue();
    assertEquals(2, saved.size());
    assertTrue(saved.stream().allMatch(c -> c.getEstado() == TipoEstadoEnum.False));
  }

  @Test
  void procesarEvento_clienteEliminado_clienteNoExiste_manejado() {
    ClienteEliminadoEvent event = new ClienteEliminadoEvent(40L, "NoExiste");

    when(clienteRepository.findById(40L)).thenReturn(Optional.empty());

    // No debe lanzar excepción (el método lo captura)
    assertDoesNotThrow(() -> listener.procesarEvento(event));

    verify(clienteRepository, times(1)).findById(40L);
    verify(cuentasRepository, never()).findByCliente(any());
    verify(cuentasRepository, never()).saveAll(anyList());
  }

  @Test
  void procesarEvento_eventoDesconocido_noHaceNada() {
    // Creamos una subclase anonima del AbstractEvent con accion desconocida
    AbstractEvent unknown = new AbstractEvent("UNKNOWN_ACTION") {
    };

    assertDoesNotThrow(() -> listener.procesarEvento(unknown));

    verifyNoInteractions(clienteRepository);
    verifyNoInteractions(cuentasRepository);
  }

}
