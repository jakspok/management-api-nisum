package com.clients.api.rest.management.servicesImpl;

import com.clients.api.rest.management.ImplServices.ClientServiceImpl;
import com.clients.api.rest.management.ImplServices.TokenServiceImpl;
import com.clients.api.rest.management.model.Client;
import com.clients.api.rest.management.model.ClientResponse;
import com.clients.api.rest.management.model.UserToken;
import com.clients.api.rest.management.repository.ClientRepository;
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

class ClientServiceImplTest {

    @Mock
    private ClientRepository repositoryClient;

    @Mock
    private TokenServiceImpl tokenServiceImpl;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByClientId_Success() {
        // Arrange
        String clientId = "1";
        Client mockClient = Client.builder().id(1L).name("John Doe").build();
        when(repositoryClient.findById(1L)).thenReturn(Optional.of(mockClient));

        // Act
        ResponseEntity<Client> response = clientService.findByClientId(clientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockClient, response.getBody());
        verify(repositoryClient, times(1)).findById(1L);
    }

    @Test
    void testFindByClientId_NotFound() {
        // Arrange
        String clientId = "1";
        when(repositoryClient.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Client> response = clientService.findByClientId(clientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repositoryClient, times(1)).findById(1L);
    }

    @Test
    void testSaveClient_Success() {
        // Arrange
        Client mockClient = Client.builder().name("John Doe").email("john@dominio.cl").password("securepassword").build();
        Client savedClient = Client.builder().id(1L).name("John Doe").email("john@dominio.cl").build();
        UserToken mockToken = UserToken.builder().token("mockToken").build();

        when(repositoryClient.findByEmail("john@dominio.cl")).thenReturn(Optional.empty());
        when(tokenServiceImpl.getToken("John Doe")).thenReturn(mockToken);
        when(repositoryClient.save(any(Client.class))).thenReturn(savedClient);

        // Act
        ResponseEntity<ClientResponse> response = clientService.saveClient(mockClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(repositoryClient, times(1)).save(any(Client.class));
    }

    @Test
    void testSaveClient_EmailAlreadyExists() {
        // Arrange
        Client mockClient = Client.builder().email("john@dominio.cl").build();
        when(repositoryClient.findByEmail("john@dominio.cl")).thenReturn(Optional.of(mockClient));

        // Act
        ResponseEntity<ClientResponse> response = clientService.saveClient(mockClient);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El correo ya esta registrado.", response.getBody().getMessage());
        verify(repositoryClient, never()).save(any(Client.class));
    }

    @Test
    void testListClient_Success() {
        // Arrange
        Client client1 = Client.builder().id(1L).name("John Doe").build();
        Client client2 = Client.builder().id(2L).name("Jane Doe").build();
        List<Client> mockClients = Arrays.asList(client1, client2);

        when(repositoryClient.findAll()).thenReturn(mockClients);

        // Act
        ResponseEntity<List<Client>> response = clientService.listClient();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(repositoryClient, times(1)).findAll();
    }

    @Test
    void testListClient_NoContent() {
        // Arrange
        when(repositoryClient.findAll()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<Client>> response = clientService.listClient();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repositoryClient, times(1)).findAll();
    }

    @Test
    void testDeleteClient_Success() {
        // Arrange
        String clientId = "1";
        when(repositoryClient.findById(1L)).thenReturn(Optional.of(new Client()));

        // Act
        ResponseEntity<String> response = clientService.deleteClient(clientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repositoryClient, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClient_NotFound() {
        // Arrange
        String clientId = "1";
        when(repositoryClient.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = clientService.deleteClient(clientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repositoryClient, never()).deleteById(anyLong());
    }
}

