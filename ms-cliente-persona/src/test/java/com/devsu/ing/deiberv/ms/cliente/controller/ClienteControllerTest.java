package com.devsu.ing.deiberv.ms.cliente.controller;

import com.devsu.ing.deiberv.ms.cliente.dtos.ClienteRequest;
import com.devsu.ing.deiberv.ms.cliente.fixture.ClienteFixture;
import com.devsu.ing.deiberv.ms.cliente.service.interfaces.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ClienteController.class})
@ContextConfiguration(classes = ClienteController.class)
class ClienteControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private ClienteService clienteService;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void listar() throws Exception {
    Mockito.when(clienteService.listarClientes())
      .thenReturn(ClienteFixture.obtenerListadoClienteVm());
    mockMvc.perform(get("/clientes"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void buscarPorId() throws Exception {
    Mockito.when(clienteService.buscarCliente(any(Long.class)))
      .thenReturn(ClienteFixture.obtenerClienteVm());
    mockMvc.perform(get("/clientes/{clienteId}", 123))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void crear() throws Exception {
    var request = ClienteFixture.obtenerClienteRequest();
    Mockito.when(clienteService.crearCliente(any(ClienteRequest.class)))
      .thenReturn(ClienteFixture.obtenerClienteVm());
    mockMvc.perform(post("/clientes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void actualizar() throws Exception {
    var request = ClienteFixture.obtenerClienteRequest();
    Mockito.when(clienteService.actualizarCliente(any(Long.class), any(ClienteRequest.class)))
      .thenReturn(ClienteFixture.obtenerClienteVm());
    mockMvc.perform(patch("/clientes/{clienteId}", 123)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void eliminar() throws Exception {
    Mockito.doNothing().when(clienteService).eliminarCliente(any(Long.class));
    mockMvc.perform(delete("/clientes/{clienteId}", 123))
      .andDo(print())
      .andExpect(status().isOk());
  }
}
