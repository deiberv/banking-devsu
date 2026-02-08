package com.devsu.ing.deiberv.ms.cuenta.service.impl;

import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuenta.exception.EnumError;
import com.devsu.ing.deiberv.ms.cuenta.exception.SimpleException;
import com.devsu.ing.deiberv.ms.cuenta.repository.ClienteRepository;
import com.devsu.ing.deiberv.ms.cuenta.service.interfaces.ClientesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientesServiceImpl implements ClientesService {

  private final ClienteRepository clienteRepository;

  @Override
  public Cliente buscarCliente(Long clienteId) {
    return clienteRepository.findById(clienteId)
      .orElseThrow(() -> new SimpleException(EnumError.CREAR_CUENTA_CLIENTE_NOT_FOUND));
  }
}
