package com.clients.api.rest.management.services;


import com.clients.api.rest.management.model.Client;
import com.clients.api.rest.management.model.ClientResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IClientService {

    ResponseEntity<Client> findByClientId(String documentId);

    ResponseEntity<ClientResponse> saveClient(Client client);

    ResponseEntity<Client> updateClient(String id,Client client);

    ResponseEntity<String> deleteClient(String deleteClientId);

    Boolean validEmail(Client client);

    ResponseEntity<List<Client>> listClient();
}
