package com.devsu.ing.deiberv.ms.cliente.service.impl;

import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteDto;
import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import com.devsu.ing.deiberv.ms.cliente.enums.EstadoClienteEnum;
import com.devsu.ing.deiberv.ms.cliente.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cliente.fixture.ClienteFixture;
import com.devsu.ing.deiberv.ms.cliente.mapper.ClienteMapper;
import com.devsu.ing.deiberv.ms.cliente.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cliente.service.publisher.ClienteEventProducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClienteServiceImplTest {

  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private ClienteMapper mapper;

  @Mock
  private ClienteEventProducer clienteEventProducer;

  @InjectMocks
  private ClienteServiceImpl clienteService;

  private AutoCloseable closeable;

  @BeforeEach
  public void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void tearDown() throws Exception {
    if (closeable != null) closeable.close();
  }

  @Test
  public void listarClientes_debeRetornarListado() {
    var listaEntities = ClienteFixture.obtenerListaClienteEntity();
    var listaDto = ClienteFixture.obtenerListadoClienteVm();

    when(clienteRepository.findAll(Sort.by(Sort.Order.by("nombre")))).thenReturn(listaEntities);
    when(mapper.toClienteDto(listaEntities.getFirst())).thenReturn(listaDto.getFirst());

    List<ClienteDto> resultado = clienteService.listarClientes();

    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals(listaDto.getFirst().getClienteId(), resultado.getFirst().getClienteId());
    verify(clienteRepository, times(1)).findAll(Sort.by(Sort.Order.by("nombre")));
    verify(mapper, times(1)).toClienteDto(listaEntities.getFirst());
  }

  @Test
  public void buscarCliente_cuandoExiste_debeRetornarDto() {
    Cliente cliente = ClienteFixture.obtenerCliente();
    ClienteDto dto = ClienteFixture.obtenerClienteVm();

    when(clienteRepository.findById(cliente.getClienteId())).thenReturn(Optional.of(cliente));
    when(mapper.toClienteDto(cliente)).thenReturn(dto);

    ClienteDto resultado = clienteService.buscarCliente(cliente.getClienteId());

    assertNotNull(resultado);
    assertEquals(dto.getClienteId(), resultado.getClienteId());
    verify(clienteRepository, times(1)).findById(cliente.getClienteId());
    verify(mapper, times(1)).toClienteDto(cliente);
  }

  @Test
  public void buscarCliente_cuandoNoExiste_debeLanzarSimpleException() {
    when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

    assertThrows(SimpleException.class, () -> clienteService.buscarCliente(999L));
    verify(clienteRepository, times(1)).findById(999L);
  }

  @Test
  public void crearCliente_debeGuardarYPublicarEvento() {
    ClienteRequest request = ClienteFixture.obtenerClienteRequest();
    Cliente clienteEntity = ClienteFixture.obtenerCliente();
    ClienteDto dto = ClienteFixture.obtenerClienteVm();

    when(mapper.toCliente(request)).thenReturn(clienteEntity);
    when(mapper.toClienteDto(clienteEntity)).thenReturn(dto);
    when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);

    ClienteDto resultado = clienteService.crearCliente(request);

    assertNotNull(resultado);
    assertEquals(dto.getClienteId(), resultado.getClienteId());
    verify(mapper, times(1)).toCliente(request);
    verify(clienteRepository, times(1)).save(clienteEntity);
    verify(mapper, times(1)).toClienteDto(clienteEntity);
    verify(clienteEventProducer, times(1)).publicarEvento(any());
  }

  @Test
  public void actualizarCliente_cuandoExiste_debeActualizarYPublicarEvento() {
    ClienteRequest request = ClienteFixture.obtenerClienteRequest();
    Cliente clienteEntity = ClienteFixture.obtenerCliente();
    clienteEntity.setNombre("Nombre Cliente");
    ClienteDto dto = ClienteFixture.obtenerClienteVm();

    when(clienteRepository.findById(clienteEntity.getClienteId())).thenReturn(Optional.of(clienteEntity));
    when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEntity);
    when(mapper.toClienteDto(clienteEntity)).thenReturn(dto);

    ClienteDto resultado = clienteService.actualizarCliente(clienteEntity.getClienteId(), request);

    assertNotNull(resultado);
    assertEquals(dto.getClienteId(), resultado.getClienteId());
    verify(clienteRepository, times(1)).findById(clienteEntity.getClienteId());
    verify(clienteRepository, times(1)).save(any(Cliente.class));
    verify(clienteEventProducer, times(1)).publicarEvento(any());
  }

  @Test
  public void actualizarCliente_cuandoNoExiste_debeLanzarSimpleException() {
    when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

    assertThrows(SimpleException.class, () -> clienteService.actualizarCliente(999L, ClienteFixture.obtenerClienteRequest()));
    verify(clienteRepository, times(1)).findById(999L);
  }

  @Test
  public void eliminarCliente_debeMarcarComoFalseYPublicarEvento() {
    Cliente clienteEntity = ClienteFixture.obtenerCliente();
    when(clienteRepository.findById(clienteEntity.getClienteId())).thenReturn(Optional.of(clienteEntity));
    when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

    clienteService.eliminarCliente(clienteEntity.getClienteId());

    ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
    verify(clienteRepository, times(1)).save(captor.capture());
    Cliente saved = captor.getValue();
    assertEquals(EstadoClienteEnum.FALSE, saved.getEstado());
    verify(clienteEventProducer, times(1)).publicarEvento(any());
  }

  @Test
  public void crearCliente_debeFallarIdentificacionExistente() {
    ClienteRequest request = ClienteFixture.obtenerClienteRequest();

    // Simular que ya existe un cliente con la misma identificacion
    when(clienteRepository.existsByIdentificacion(request.getIdentificacion())).thenReturn(true);

    assertThrows(SimpleException.class, () -> clienteService.crearCliente(request));

    // Verificar que no se intentó guardar ni publicar evento
    verify(clienteRepository, times(0)).save(any(Cliente.class));
    verify(clienteEventProducer, times(0)).publicarEvento(any());
  }

}
