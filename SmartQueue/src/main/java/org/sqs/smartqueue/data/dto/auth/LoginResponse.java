package org.sqs.smartqueue.data.dto.auth;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponse {
    private Long expiresIn;
    private String role;
    private UUID userId;

}
