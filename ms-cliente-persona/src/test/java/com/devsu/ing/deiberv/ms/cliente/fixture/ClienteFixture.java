package com.devsu.ing.deiberv.ms.cliente.fixture;

import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteDto;
import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import com.devsu.ing.deiberv.ms.cliente.enums.EstadoClienteEnum;
import com.devsu.ing.deiberv.ms.cliente.enums.GeneroEnum;

import java.util.List;

public class ClienteFixture {

  public static List<Cliente> obtenerListaClienteEntity() {
    return List.of(Cliente.builder()
      .clienteId(123L)
      .nombre("Nombre Cliente")
      .estado(EstadoClienteEnum.TRUE)
      .direccion("Direccion")
      .edad(39L)
      .genero(GeneroEnum.MASCULINO)
      .password("123456")
      .build());
  }

  public static List<Cliente> obtenerListaClienteEntityVacia() {
    return List.of();
  }

  public static Cliente obtenerCliente() {
    return Cliente.builder()
      .clienteId(123L)
      .nombre("Nombre Cliente")
      .estado(EstadoClienteEnum.TRUE)
      .direccion("Direccion")
      .edad(39L)
      .genero(GeneroEnum.FEMENINO)
      .password("123456")
      .identificacion("E-14596523")
      .telefono("74859632")
      .build();
  }

  public static ClienteRequest obtenerClienteRequest() {
    return ClienteRequest.builder()
      .nombre("Nombre Cliente")
      .genero(GeneroEnum.OTRO)
      .edad(39L)
      .identificacion("E-14596523")
      .direccion("Direccion")
      .telefono("74859632")
      .password("123456")
      .build();
  }

  public static List<ClienteDto> obtenerListadoClienteVm() {
    return List.of(obtenerClienteVm());
  }

  public static ClienteDto obtenerClienteVm() {
    return ClienteDto.builder()
      .clienteId(123L)
      .nombre("Nombre Cliente")
      .genero(GeneroEnum.MASCULINO.name())
      .edad(39L)
      .identificacion("E-14596523")
      .direccion("Direccion")
      .telefono("74859632")
      .estado(EstadoClienteEnum.getEstadoString(EstadoClienteEnum.TRUE))
      .build();
  }

}
