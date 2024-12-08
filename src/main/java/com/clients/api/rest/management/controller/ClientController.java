package com.clients.api.rest.management.controller;

import com.clients.api.rest.management.model.Client;
import com.clients.api.rest.management.model.ClientResponse;
import com.clients.api.rest.management.services.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/client-management")
@Tag(name = "Client Management", description = "API para gestionar clientes")
public class ClientController {

  private static final Logger LOGGER = LogManager.getLogger(ClientController.class);
  private final IClientService iClientService;

  @Autowired
  public ClientController(final IClientService iClientService) {
    this.iClientService = iClientService;
  }

  @Operation(
      summary = "Obtener cliente por ID",
      description = "Retorna un cliente basado en su ID.")
  @GetMapping("/client/{id}")
  public ResponseEntity<Client> getClientById(
      @Parameter(description = "ID del cliente", required = true) @PathVariable(value = "id")
          String id) {
    LOGGER.info("Init getClientById Client Id: {}", id);
    return iClientService.findByClientId(id);
  }

  @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente en el sistema.")
  @PostMapping("/client")
  public ResponseEntity<ClientResponse> createClient(
      @Parameter(
              description = "Objeto cliente con los datos necesarios para su creación",
              required = true)
          @RequestBody
          Client clientCreate) {
    LOGGER.info("Init createClient Client Id: {}", clientCreate.getId());
    return iClientService.saveClient(clientCreate);
  }

  @Operation(
      summary = "Actualizar cliente",
      description = "Actualiza los datos de un cliente existente.")
  @PutMapping("/client/{id}")
  public ResponseEntity<Client> updateClient(
      @Parameter(description = "ID del cliente a actualizar", required = true)
          @PathVariable(value = "id")
          String id,
      @Parameter(description = "Datos del cliente actualizados", required = true) @RequestBody
          Client client) {
    LOGGER.info("Init updateClient client controller : {}\n body : {}", client);
    return iClientService.updateClient(id, client);
  }

  @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema.")
  @DeleteMapping("/client/{id}")
  public ResponseEntity<String> deleteClient(
      @Parameter(description = "ID del cliente a eliminar", required = true)
          @PathVariable(value = "id")
          String id) {
    LOGGER.info("Init deleteClient client  : {}\n body : {}", id);
    return iClientService.deleteClient(id);
  }

  @Operation(
      summary = "Listar clientes",
      description = "Retorna una lista de todos los clientes registrados.")
  @GetMapping("/client/listClients")
  public ResponseEntity<List<Client>> listClients() {
    LOGGER.info("Init listClients controller : {}\n body : {}");
    return iClientService.listClient();
  }
}
