package com.vietcuong.simpleJwt.controller;

import com.vietcuong.simpleJwt.entity.Client;
import com.vietcuong.simpleJwt.service.ClientService;
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

    @PostMapping("/clientRegister")
    public ResponseEntity<?> clientRegister(@RequestBody Client clientRequest) {
        return ResponseEntity.ok(clientService.clientRegister(clientRequest));
    }


}
