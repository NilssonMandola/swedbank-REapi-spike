package com.crankoid.reverseengineeringapi.resource.login.api;

import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginRequestDTO;
import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginResponseDTO;
import org.springframework.web.bind.annotation.*;

@RequestMapping("login")
public interface LoginResource {
    @PostMapping
    public LoginResponseDTO login(@RequestBody(required = true) LoginRequestDTO loginRequest);

    @GetMapping("/{bic}")
    public LoginResponseDTO poll(@PathVariable(required = true) String bic);
}
