package org.ipt.smartqueue.data.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String role;
    private String provider;
}
