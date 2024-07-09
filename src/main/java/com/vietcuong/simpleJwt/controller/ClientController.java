package com.vietcuong.simpleJwt.controller;

import com.vietcuong.simpleJwt.entity.Client;
import com.vietcuong.simpleJwt.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Handle POST requests to "/clientRegister" endpoint
    @PostMapping("/clientRegister")
    public ResponseEntity<?> clientRegister(
            @Valid @RequestBody Client clientRequest) { // @Valid annotation
        // triggers validation
        return ResponseEntity.ok(clientService.clientRegister(clientRequest));
    }
}
