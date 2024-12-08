package com.clients.api.rest.management.ImplServices;


import com.clients.api.rest.management.model.Client;
import com.clients.api.rest.management.model.ClientResponse;
import com.clients.api.rest.management.model.UserToken;
import com.clients.api.rest.management.repository.ClientRepository;
import com.clients.api.rest.management.services.IClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {

    private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);
    private final ClientRepository repositoryClient;
    private final TokenServiceImpl tokenServiceImpl;



    @Autowired
    public ClientServiceImpl(final ClientRepository repository, TokenServiceImpl tokenServiceImpl) {

        this.repositoryClient = repository;
        this.tokenServiceImpl = tokenServiceImpl;
    }

    @Override
    public ResponseEntity<Client> findByClientId(final String documentId) {
        try {
            Long id = Long.valueOf(documentId);

            Optional<Client> optionalClient = repositoryClient.findById(id);

            if (optionalClient.isPresent()) {
                Client client = optionalClient.get();
                LOGGER.info("Returned client: {}", client);
                return ResponseEntity.ok(client);
            } else {
                LOGGER.warn("Client not found with ID: {}", documentId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (NumberFormatException ex) {
            LOGGER.error("Invalid document ID format: {}. Cause: {}", documentId, ex.getMessage(), ex);
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            LOGGER.error("Unexpected error occurred while finding client with ID: {}. Cause: {}", documentId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @Override
    public Boolean validEmail(final Client client) {
        final String regex = "^[a-zA-Z0-9._%+-]+@dominio\\.cl$";

        return Optional.ofNullable(client)
                .map(Client::getEmail)
                .map(email -> email.matches(regex))
                .orElse(false);
    }

    @Override
    public ResponseEntity<ClientResponse> saveClient(final Client client) {

        // Verificar si el email es válido
        if (validEmail(client)) {
            try {

                Optional<Client> existingClient = repositoryClient.findByEmail(client.getEmail());
                if (existingClient.isPresent()) {
                    LOGGER.warn("Email already registered: {}", client.getEmail());
                    return ResponseEntity.status(HttpStatus.CONFLICT) // Error 409 - Conflict
                            .body(ClientResponse.builder()
                                    .message("El correo ya esta registrado.")
                                    .build());
                }


                LocalDateTime now = LocalDateTime.now();
                LocalDateTime newDateTime = now.plusHours(24);

                UserToken userToken = tokenServiceImpl.getToken(client.getName());

                // Crear el objeto Client a guardar
                Client clientToSave = Client.builder()
                        .name(client.getName())
                        .email(client.getEmail())
                        .password(client.getPassword())
                        .dataAdded(LocalDate.now())
                        .startDate(LocalDate.now())
                        .token(userToken.getToken ())
                        .endDate(LocalDate.from(newDateTime))
                        .build();

                // Asignar el cliente a cada teléfono antes de guardarlos
                if (client.getPhones() != null && !client.getPhones().isEmpty()) {
                    client.getPhones().forEach(phone -> phone.setClient(clientToSave));
                    clientToSave.setPhones(client.getPhones());
                }

                Client savedClient = repositoryClient.save(clientToSave);

                LOGGER.info("Returned client: {}", savedClient);
                return ResponseEntity.ok(
                        ClientResponse.builder()
                                .id (savedClient.getId ())
                                .name (savedClient.getName ())
                                .created (savedClient.getStartDate())
                                .modified (savedClient.getDataAdded())
                                .lastLogin(savedClient.getStartDate())
                                .token(savedClient.getToken())
                                .isActive (true)
                        .build());

            } catch (Exception e) {
                LOGGER.error("Error saving client in BD: {} - client with ID: {}", e.getMessage(), client.getId(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)  // Error 500
                        .body(null);
            }
        } else {
            LOGGER.warn("Invalid email for client: {}", client.getEmail());
            return ResponseEntity.badRequest().body(null);  // Error 400 - Bad Request
        }
    }




    @Override
    public ResponseEntity<Client> updateClient(final String id, final Client client) {
        try {
            // Intentar encontrar el cliente por ID
            Optional<Client> optionalClient = repositoryClient.findById(Long.valueOf(id));

            // Si el cliente existe, se actualiza
            if (optionalClient.isPresent()) {
                Client existingClient = optionalClient.get();

                // Actualizar los campos del cliente
                existingClient.setName(client.getName());
                existingClient.setEmail(client.getEmail());
                existingClient.setPassword(client.getPassword());  // Si se permite la actualización de la contraseña

                // Actualizar los teléfonos
                if (client.getPhones() != null) {
                    // Limpiar los teléfonos existentes y reemplazar con los nuevos
                    existingClient.getPhones().clear();
                    client.getPhones().forEach(phone -> {
                        phone.setClient(existingClient); // Asegurar la relación bidireccional
                        existingClient.getPhones().add(phone);
                    });
                }

                // Guardar el cliente actualizado
                Client updatedClient = repositoryClient.save(existingClient);

                LOGGER.info("Updated client: {}", updatedClient);
                return ResponseEntity.ok(updatedClient);  // Retornar el cliente actualizado con OK status
            } else {
                LOGGER.warn("Client with ID {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Retornar NOT_FOUND si no se encuentra el cliente
            }

        } catch (Exception e) {
            LOGGER.error("Error updating client in BD: {} - client with ID: {}", e.getMessage(), id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Retornar INTERNAL_SERVER_ERROR en caso de error
        }
    }



    @Override
    public ResponseEntity<String> deleteClient(final String deleteClientId) {
        try {
            // Intentar encontrar el cliente por ID
            Optional<Client> optionalClient = repositoryClient.findById(Long.valueOf(deleteClientId));

            // Si el cliente existe, se elimina
            if (optionalClient.isPresent()) {
                repositoryClient.deleteById(Long.valueOf(deleteClientId));
                LOGGER.info("Client with ID {} deleted successfully.", deleteClientId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("El cliente con ID " + deleteClientId + " fue eliminado exitosamente.");
            } else {
                LOGGER.warn("Client with ID {} not found.", deleteClientId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró un cliente con el ID " + deleteClientId + ".");
            }
        } catch (NumberFormatException ex) {
            LOGGER.error("Invalid client ID format: {}. Cause: {}", deleteClientId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El formato del ID proporcionado (" + deleteClientId + ") no es válido.");
        } catch (Exception ex) {
            LOGGER.error("Error deleting client with ID {}. Cause: {}", deleteClientId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado al intentar eliminar el cliente con ID " + deleteClientId + ".");
        }
    }



    @Override
    public ResponseEntity<List<Client>> listClient() {
        try {

            List<Client> clients = repositoryClient.findAll();

            if (clients.isEmpty()) {
                LOGGER.info("No clients found in the database.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
            }

            LOGGER.info("Retrieved {} clients from the database.", clients.size());
            return ResponseEntity.ok(clients);

        } catch (Exception e) {
            LOGGER.error("Error searching clients in the database: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }



}
