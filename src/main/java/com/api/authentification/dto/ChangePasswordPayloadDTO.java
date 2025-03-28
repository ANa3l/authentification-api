package com.api.authentification.dto;

import lombok.Data;

@Data
public class ChangePasswordPayloadDTO {
    private String currentPassword;
    private String newPassword;
}
