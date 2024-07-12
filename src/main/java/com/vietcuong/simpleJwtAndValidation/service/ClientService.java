package com.vietcuong.simpleJwtAndValidation.service;

import com.vietcuong.simpleJwtAndValidation.entity.Client;
import com.vietcuong.simpleJwtAndValidation.repository.ClientRepository;
import com.vietcuong.simpleJwtAndValidation.response.ClientResponse;
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
