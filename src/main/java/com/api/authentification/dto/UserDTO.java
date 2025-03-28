package com.api.authentification.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String passwordHash;
}
