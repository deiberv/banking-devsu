package com.devsu.ing.deiberv.ms.cliente.mapper;

import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteDto;
import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import com.devsu.ing.deiberv.ms.cliente.enums.EstadoClienteEnum;
import com.devsu.ing.deiberv.ms.cliente.enums.GeneroEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

  @Mapping(source = "genero", target = "genero", qualifiedByName = "generoToString")
  @Mapping(source = "estado", target = "estado", qualifiedByName = "estadoToString")
  ClienteDto toClienteDto(Cliente cliente);

  @Mapping(source = "genero", target = "genero", qualifiedByName = "stringToGenero")
  @Mapping(source = "estado", target = "estado", qualifiedByName = "stringToEstado")
  Cliente toCliente(ClienteRequest clienteDto);

  void updateEntityFromDto(ClienteRequest dto, @MappingTarget Cliente entity);

  @Named("generoToString")
  default String generoToString(GeneroEnum genero) {
    return genero == null ? null : genero.name();
  }

  @Named("stringToGenero")
  default GeneroEnum stringToGenero(String genero) {
    if (genero == null) return null;
    try {
      return GeneroEnum.valueOf(genero.trim().toUpperCase());
    } catch (IllegalArgumentException ex) {
      return null; // o lanzar excepción según la política de la app
    }
  }

  @Named("stringToEstado")
  default EstadoClienteEnum stringToEstado(String estado) {
    if (estado == null) return null;
    try {
      return EstadoClienteEnum.valueOf(estado.trim().toUpperCase());
    } catch (IllegalArgumentException ex) {
      return null; // o lanzar excepción según la política de la app
    }
  }

  @Named("estadoToString")
  default String estadoToString(EstadoClienteEnum genero) {
    return genero == null ? null : genero.name();
  }

}
