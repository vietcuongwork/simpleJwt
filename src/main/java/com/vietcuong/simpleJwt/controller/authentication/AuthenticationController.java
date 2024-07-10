package com.vietcuong.simpleJwt.controller.authentication;

import com.vietcuong.simpleJwt.Error;
import com.vietcuong.simpleJwt.entity.authentication.User;
import com.vietcuong.simpleJwt.response.AuthenticationErrorResponse;
import com.vietcuong.simpleJwt.service.authentication.AuthenticationService;
import com.vietcuong.simpleJwt.service.authentication.TokenService;
import com.vietcuong.simpleJwt.service.authentication.UserDetailsServiceImpl;
import com.vietcuong.simpleJwt.service.authentication.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class AuthenticationController {

    private final AuthenticationService authenticationService;


    private final UserService userService;

    private final TokenService tokenService;

    public AuthenticationController(AuthenticationService authenticationService,
            UserService userService, UserDetailsServiceImpl userDetailsService,
            TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.tokenService = tokenService;
    }


    @PostMapping("/register")
    public AuthenticationErrorResponse register(@RequestBody User userRequest) {
        AuthenticationErrorResponse response = new AuthenticationErrorResponse();
        Map<String, String> detail = new HashMap<>();
        if (userService.existsByUsername(userRequest.getUsername())) {
            response.setStatusCode(Error.GlobalError.USER_REGISTRATION_ERROR.getCode());
            response.setDescription(Error.GlobalError.USER_REGISTRATION_ERROR.getDescription());
            detail.put("error", "Username has already been used");
            response.setDetail(detail);
            return response;
        }
        response.setStatusCode(Error.GlobalError.USER_REGISTRATION_SUCCESS.getCode());
        response.setDescription(Error.GlobalError.CLIENT_REGISTRATION_SUCCESS.getDescription());
        detail = response.authenticationResponseMapping(authenticationService.registerResponse(userRequest));
        response.setDetail(detail);
        return response;
    }

/*    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userRequest) {
        if (!userService.existsByUsername(userRequest.getUsername())) {
            return new ResponseEntity<String>("Incorrect username",
                    HttpStatus.BAD_REQUEST);
        }
        if (!userService.checkPassword(userRequest.getUsername(),
                userRequest.getPassword())) {
            return new ResponseEntity<String>("Incorrect password",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(
                authenticationService.authenticationResponse(userRequest));
    }*/

    @PostMapping("/login")
    public AuthenticationErrorResponse login(@RequestBody User userRequest) {
        AuthenticationErrorResponse response = new AuthenticationErrorResponse();
        Map<String, String> detail = new HashMap<>();
        response.setStatusCode(Error.GlobalError.CLIENT_REGISTRATION_ERROR.getCode());
        response.setDescription(Error.GlobalError.CLIENT_REGISTRATION_ERROR.getDescription());
        if(!userService.existsByUsername(userRequest.getUsername())) {
            detail.put("error", "Incorrect username");
            response.setDetail(detail);
            return response;
        }
        if (!userService.checkPassword(userRequest.getUsername(),
                userRequest.getPassword())) {
            detail.put("error", "Incorrect password");
            response.setDetail(detail);
            return response;
        }
        response.setStatusCode(Error.GlobalError.USER_REGISTRATION_SUCCESS.getCode());
        response.setDescription(Error.GlobalError.USER_REGISTRATION_SUCCESS.getDescription());
        detail = response.authenticationResponseMapping(authenticationService.authenticationResponse(userRequest));
        response.setDetail(detail);
        return response;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<String>> getAllUser() {
        List<String> userList = userService.allUsersToString();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/getAllTokens")
    public ResponseEntity<List<String>> getAllToken() {
        List<String> tokenList = tokenService.allTokensToString();
        return ResponseEntity.ok(tokenList);
    }

    @DeleteMapping("/admin/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody User userRequest) {
        if (!userService.existsByUsername(userRequest.getUsername())) {
            return new ResponseEntity<String>("User doesn't exist",
                    HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(userRequest.getUsername());
        return ResponseEntity.ok("User deleted successfully");
    }


}
