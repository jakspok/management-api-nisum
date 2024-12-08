package com.clients.api.rest.management.controller;

import com.clients.api.rest.management.model.Client;
import com.clients.api.rest.management.model.ClientResponse;
import com.clients.api.rest.management.services.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private IClientService iClientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientById_Success() {
        // Arrange
        String clientId = "1";
        Client mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setName("John Doe");
        when(iClientService.findByClientId(clientId)).thenReturn(ResponseEntity.ok(mockClient));

        // Act
        ResponseEntity<Client> response = clientController.getClientById(clientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockClient, response.getBody());
        verify(iClientService, times(1)).findByClientId(clientId);
    }

    @Test
    void testCreateClient_Success() {
        // Arrange
        Client mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setName("John Doe");

        ClientResponse mockResponse = ClientResponse.builder().id(1L).isActive(true).build();
        when(iClientService.saveClient(mockClient)).thenReturn(ResponseEntity.ok(mockResponse));

        // Act
        ResponseEntity<ClientResponse> response = clientController.createClient(mockClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(iClientService, times(1)).saveClient(mockClient);
    }

    @Test
    void testUpdateClient_Success() {
        // Arrange
        String clientId = "1";
        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setName("John Doe Updated");

        when(iClientService.updateClient(clientId, updatedClient)).thenReturn(ResponseEntity.ok(updatedClient));

        // Act
        ResponseEntity<Client> response = clientController.updateClient(clientId, updatedClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedClient, response.getBody());
        verify(iClientService, times(1)).updateClient(clientId, updatedClient);
    }

    @Test
    void testDeleteClient_Success() {
        // Arrange
        String clientId = "1";
        String mockMessage = "Cliente eliminado correctamente";
        when(iClientService.deleteClient(clientId)).thenReturn(ResponseEntity.ok(mockMessage));

        // Act
        ResponseEntity<String> response = clientController.deleteClient(clientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockMessage, response.getBody());
        verify(iClientService, times(1)).deleteClient(clientId);
    }

    @Test
    void testListClients_Success() {
        // Arrange
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("John Doe");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Jane Doe");

        List<Client> mockClients = Arrays.asList(client1, client2);
        when(iClientService.listClient()).thenReturn(ResponseEntity.ok(mockClients));

        // Act
        ResponseEntity<List<Client>> response = clientController.listClients();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockClients, response.getBody());
        verify(iClientService, times(1)).listClient();
    }
}
