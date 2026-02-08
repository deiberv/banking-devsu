package com.devsu.ing.deiberv.ms.cliente.service.interfaces;

import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteDto;
import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteRequest;

import java.util.List;

public interface ClienteService {

  /**
   * Obtiene el listado de clientes
   * @return {@link List<ClienteDto>}
   */
  List<ClienteDto> listarClientes();

  /**
   * Obtiene informacion del cliente indicado
   * @param clienteId {@link Long}
   * @return {@link ClienteDto}
   */
  ClienteDto buscarCliente(Long clienteId);

  /**
   * Crea un cliente
   * @param clienteRequest {@link ClienteRequest}
   * @return {@link ClienteDto}
   */
  ClienteDto crearCliente(ClienteRequest clienteRequest);

  /**
   * Actualiza la informacion del cliente indicado
   * @param clienteId {@link Long}
   * @param clienteRequest {@link ClienteRequest}
   * @return {@link ClienteDto}
   */
  ClienteDto actualizarCliente(Long clienteId, ClienteRequest clienteRequest);

  /**
   * Elimina el cliente indicado de la base de datos.
   * Se realiza una eliminacion logica
   * @param clienteId {@link Long}
   */
  void eliminarCliente(Long clienteId);

}
