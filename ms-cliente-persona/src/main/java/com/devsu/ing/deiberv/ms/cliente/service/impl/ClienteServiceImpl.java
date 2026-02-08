package com.devsu.ing.deiberv.ms.cliente.service.impl;

import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteDto;
import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import com.devsu.ing.deiberv.ms.cliente.enums.EstadoClienteEnum;
import com.devsu.ing.deiberv.ms.cliente.events.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.cliente.events.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.cliente.exception.EnumError;
import com.devsu.ing.deiberv.ms.cliente.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cliente.mapper.ClienteMapper;
import com.devsu.ing.deiberv.ms.cliente.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cliente.service.interfaces.ClienteService;
import com.devsu.ing.deiberv.ms.cliente.service.publisher.ClienteEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository clienteRepository;
  private final ClienteMapper mapper;
  private final ClienteEventProducer clienteEventProducer;

  @Override
  public List<ClienteDto> listarClientes() {
    return this.clienteRepository.findAll(Sort.by(Sort.Order.by("nombre")))
      .stream().map(this::convertToClienteDto)
      .toList();
  }

  @Override
  public ClienteDto buscarCliente(Long clienteId) {
    log.debug("Buscando información del cliente {}", clienteId);
    var cliente = this.findById(clienteId);
    return this.convertToClienteDto(cliente);
  }

  @Override
  @Transactional
  public ClienteDto crearCliente(ClienteRequest clienteRequest) {
    log.debug("Creando cliente {}", clienteRequest);
    var cliente = this.mapper.toCliente(clienteRequest);
    this.clienteRepository.save(cliente);
    this.clienteEventProducer.publicarEvento(new ClienteCreadoEvent(cliente.getClienteId(), cliente.getNombre()));
    return this.convertToClienteDto(cliente);
  }

  @Override
  public ClienteDto actualizarCliente(Long clienteId, ClienteRequest clienteRequest) {
    log.debug("Actualizando datos del cliente {} nuevos datos {}", clienteId, clienteRequest);
    var cliente = this.findById(clienteId);
    cliente.setNombre(Objects.requireNonNullElse(clienteRequest.getNombre(), cliente.getNombre()));
    cliente.setGenero(Objects.requireNonNullElse(clienteRequest.getGenero(), cliente.getGenero()));
    cliente.setEdad(Objects.requireNonNullElse(clienteRequest.getEdad(), cliente.getEdad()));
    cliente.setIdentificacion(Objects.requireNonNullElse(clienteRequest.getIdentificacion(), cliente.getIdentificacion()));
    cliente.setDireccion(Objects.requireNonNullElse(clienteRequest.getDireccion(), cliente.getDireccion()));
    cliente.setTelefono(Objects.requireNonNullElse(clienteRequest.getTelefono(), cliente.getTelefono()));
    cliente.setPassword(Objects.requireNonNullElse(clienteRequest.getPassword(), cliente.getPassword()));
    this.clienteRepository.save(cliente);
    this.clienteEventProducer.publicarEvento(new ClienteCreadoEvent(cliente.getClienteId(), cliente.getNombre()));
    return this.convertToClienteDto(cliente);
  }

  @Override
  public void eliminarCliente(Long clienteId) {
    log.debug("Eliminando de manera logica al cliente {}", clienteId);
    var cliente = this.findById(clienteId);
    cliente.setEstado(EstadoClienteEnum.FALSE);
    this.clienteRepository.save(cliente);
    this.clienteEventProducer.publicarEvento(new ClienteEliminadoEvent(cliente.getClienteId(), cliente.getNombre()));
  }


  //---------------------------
  //- Metodos privados --------
  //---------------------------
  private Cliente findById(Long clienteId) {
    return this.clienteRepository.findById(clienteId)
      .orElseThrow(() -> new SimpleException(EnumError.CLIENTE_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
  }

  private ClienteDto convertToClienteDto(Cliente cliente) {
    return this.mapper.toClienteDto(cliente);
  }

}
