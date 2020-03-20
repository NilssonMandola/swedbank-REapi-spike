package com.crankoid.reverseengineeringapi.resource.login.api;

import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginRequestDTO;
import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("login")
public interface LoginResource {
    @PostMapping
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest);

    @GetMapping()
    public LoginResponseDTO poll(@RequestBody LoginRequestDTO loginRequest);
}
