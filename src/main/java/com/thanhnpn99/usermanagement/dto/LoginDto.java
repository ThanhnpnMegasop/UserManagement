package com.thanhnpn99.usermanagement.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}


