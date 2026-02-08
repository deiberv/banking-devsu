package com.devsu.ing.deiberv.ms.cuenta.events.listeners;

import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuenta.enums.TipoEstadoEnum;
import com.devsu.ing.deiberv.ms.cuenta.events.AbstractEvent;
import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteCreadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteEliminadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.events.cliente.ClienteModificadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cuenta.repository.CuentasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteIntegracionRabbitListener {

  private final ClienteRepository clienteRepository;
  private final CuentasRepository cuentasRepository;

  @Transactional
  @RabbitListener(queues = "cliente_queue")
  public void procesarEvento(AbstractEvent event){
    switch (event.getAccion()) {
      case "CLIENTE_CREADO": procesarClienteCreado((ClienteCreadoEvent) event); break;
      case "CLIENTE_MODIFICADO": procesarClienteModificado((ClienteModificadoEvent) event); break;
      case "CLIENTE_ELIMINADO": procesarClienteEliminado((ClienteEliminadoEvent) event); break;
      default:
        log.warn("El evento que se ha emitido no es manejable por el servicio");
    }
  }


  private void procesarClienteCreado(ClienteCreadoEvent clienteCreadoEvent) {
    try {
      log.info("Mensaje recivido {} creando cliente", clienteCreadoEvent);
      this.clienteRepository.save(Cliente.builder()
        .clienteId(clienteCreadoEvent.getClienteId())
        .nombre(clienteCreadoEvent.getNombre())
        .build());
      log.info("Evento de cliente creado procesado de menera correcta");
    } catch (Exception exc) {
      log.info("Se ha producido un error procesando el evento de creacion {0}.", exc);
    }
  }

  private void procesarClienteModificado(ClienteModificadoEvent clienteModificadoEvent) {
    try {
      log.info("Mensaje recivido {} modificando cliente.", clienteModificadoEvent);
      this.clienteRepository.save(Cliente.builder()
        .clienteId(clienteModificadoEvent.getClienteId())
        .nombre(clienteModificadoEvent.getNombre())
        .build());
      log.info("Evento de cliente modificado procesado de menera correcta");
    } catch (Exception exc) {
      log.info("Se ha producido un error procesando el evento de modificacion {0}.", exc);
    }
  }

  private void procesarClienteEliminado(ClienteEliminadoEvent clienteEliminadoEvent) {
    try {
      log.info("Mensaje recivido {} eliminando cliente ", clienteEliminadoEvent);
      log.info("Se cambia el estado de las cuentas relacionadas al cliente");
      var cliente = this.clienteRepository.findById(clienteEliminadoEvent.getClienteId())
        .orElseThrow();
      var cuentasCliente = this.cuentasRepository.findByCliente(cliente)
        .stream().map(cuenta -> {
          cuenta.setEstado(TipoEstadoEnum.False);
          return cuenta;
        }).toList();
      this.cuentasRepository.saveAll(cuentasCliente);
      log.info("Evento de cliente eliminado procesado de menera correcta");
    } catch (Exception exc) {
      log.info("Se ha producido un error procesando el evento de eliminacion {0}.", exc);
    }
  }

}
