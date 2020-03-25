package com.crankoid.reverseengineeringapi.resource.login.api;

import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginRequestDTO;
import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("login")
@Tag(name = "Login")
public interface LoginResource {
    @PostMapping
    public LoginResponseDTO login(@RequestBody(required = true) LoginRequestDTO loginRequest);

    @GetMapping("/{bic}")
    public LoginResponseDTO poll(@PathVariable(required = true) String bic);
}
