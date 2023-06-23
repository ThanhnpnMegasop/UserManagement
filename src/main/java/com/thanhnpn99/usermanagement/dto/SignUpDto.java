package com.thanhnpn99.usermanagement.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private boolean active;
}
