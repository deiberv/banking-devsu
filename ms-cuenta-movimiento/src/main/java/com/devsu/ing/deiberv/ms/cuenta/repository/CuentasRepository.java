package com.devsu.ing.deiberv.ms.cuenta.repository;

import com.devsu.ing.deiberv.ms.cuenta.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuenta.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentasRepository extends JpaRepository<Cuenta, Long> {

  Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

  List<Cuenta> findByCliente(Cliente cliente);

}
