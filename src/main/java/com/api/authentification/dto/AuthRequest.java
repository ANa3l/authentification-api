package com.api.authentification.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String passwordHash;
}
