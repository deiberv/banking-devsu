package com.devsu.ing.deiberv.ms.cuenta.events.listeners;

import com.devsu.ing.deiberv.ms.cuenta.events.MovimientoCreadoEvent;
import com.devsu.ing.deiberv.ms.cuenta.repository.CuentasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovimientoCreadoListener implements ApplicationListener<MovimientoCreadoEvent> {

  private final CuentasRepository cuentasRepository;

  @Override
  public void onApplicationEvent(MovimientoCreadoEvent event) {
    var movimiento = event.getMovimiento();
    var cuenta = this.cuentasRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta()).orElseThrow();
    var nuevoSaldo = cuenta.getSaldo().add(movimiento.getValor());
    cuenta.setSaldo(nuevoSaldo);
    this.cuentasRepository.save(cuenta);
  }
}
