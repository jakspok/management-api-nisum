package com.clients.api.rest.management.controller;

import com.clients.api.rest.management.model.Client;
import com.clients.api.rest.management.model.ClientResponse;
import com.clients.api.rest.management.services.IClientService;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/client-management")
public class ClientController {

    private static final Logger LOGGER = LogManager.getLogger(ClientController.class);
    private final IClientService iClientService;

    @Autowired
    public ClientController(final IClientService iClientService) {

        this.iClientService = iClientService;
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") String id) {

        LOGGER.info("Init getClientById Client Id: {}", id);

        return iClientService.findByClientId(id);
    }

    @PostMapping("/client")
    public ResponseEntity<ClientResponse> createClient(@RequestBody Client clientCreate) {

        LOGGER.info("Init createClient Client Id: {}", clientCreate.getId());

        return iClientService.saveClient(clientCreate);

    }

    @PutMapping("/client/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") String id,
                                               @RequestBody Client client) {
        LOGGER.info("Init updateClient client controller : {}\n body : {}", client);

        return iClientService.updateClient(id,client);

    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable(value = "id") String id) {

        LOGGER.info("Init deleteClient client  : {}\n body : {}", id);

        return iClientService.deleteClient(id);
    }

    @GetMapping("/client/listClients")
    public ResponseEntity<List<Client>> listClients() {

        LOGGER.info("Init listClients controller : {}\n body : {}");

        return iClientService.listClient();
    }

}
