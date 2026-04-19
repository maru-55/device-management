package com.portfolio.devicemanagement.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private Authority authority;

    public enum Authority {
        ADMIN,
        USER,
    }
}
