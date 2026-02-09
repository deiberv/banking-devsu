package com.devsu.ing.deiberv.ms.cuenta.fixture;

import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;

public class ClientesFixture {

  public static Cliente getClienteEntity(){
    return Cliente.builder()
      .clienteId(15963L)
      .nombre("Nombre cliente")
      .build();
  }

}
