package com.api.authentification.mapper;

import com.api.authentification.dto.TokenDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public TokenDTO toDto(String token) {
        return new TokenDTO(token);
    }
}
