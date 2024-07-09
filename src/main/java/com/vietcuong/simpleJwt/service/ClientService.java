package com.vietcuong.simpleJwt.service;

import com.vietcuong.simpleJwt.entity.Client;
import com.vietcuong.simpleJwt.repository.ClientRepository;
import com.vietcuong.simpleJwt.response.ClientResponse;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    //    private final ObjectValidator<Client> clientObjectValidator;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        //        this.clientObjectValidator = objectValidator;
    }

    public ClientResponse clientRegister(Client clientRequest) {
        //        clientObjectValidator.validate(clientRequest);
        Client client = new Client();
        client.setFullName(clientRequest.getFullName());
        client.setUsername(clientRequest.getUsername());
        client.setEmail(clientRequest.getEmail());
        client.setDateOfBirth(clientRequest.getDateOfBirth());
        clientRepository.save(client);
        return new ClientResponse("Client registered successfully");
    }
}
