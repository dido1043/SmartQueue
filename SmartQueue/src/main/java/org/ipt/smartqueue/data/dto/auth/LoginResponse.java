package org.ipt.smartqueue.data.dto.auth;

import lombok.Data;
import org.ipt.smartqueue.data.model.User;

import java.util.UUID;

@Data
public class LoginResponse {
    private Long expiresIn;
    private String role;
    private UUID userId;

}
