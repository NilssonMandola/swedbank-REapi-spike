package com.crankoid.reverseengineeringapi.resource.login.internal.converter;

import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginResponseDTO;
import com.crankoid.reverseengineeringapi.service.login.api.model.LoginResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoginResponseDTOConverter implements Converter<LoginResponse, LoginResponseDTO> {
    @Override
    public LoginResponseDTO convert(LoginResponse loginResponse) {
        LoginResponseDTO dto = new LoginResponseDTO();

        switch (loginResponse.getStatus()) {
            case OK:
                dto.setStatus(LoginResponseDTO.Status.OK);
                break;
            case PENDING:
                dto.setStatus(LoginResponseDTO.Status.PENDING);
                break;
            default:
                dto.setStatus(LoginResponseDTO.Status.ERROR);
        }

        dto.setAutoStartToken(loginResponse.getAutostartToken());

        return dto;
    }
}
