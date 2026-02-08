package com.devsu.ing.deiberv.ms.cuenta.events;

import com.devsu.ing.deiberv.ms.cuenta.entity.Movimiento;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MovimientoCreadoEvent extends ApplicationEvent {
  private final Movimiento movimiento;
  public MovimientoCreadoEvent(Object source, Movimiento movimiento) {
    super(source);
    this.movimiento = movimiento;
  }
}
