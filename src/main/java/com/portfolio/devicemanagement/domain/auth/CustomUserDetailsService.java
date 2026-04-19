package com.portfolio.devicemanagement.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       return userRepository.findByUsername(username)
                .map(
                        userEntity -> new CustomUserDetails(
                                userEntity.getId(),
                                userEntity.getUsername(),
                                userEntity.getPassword(),
                                toGrantedAuthorityList(userEntity.getAuthority())
                                )
                        )
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        "Given username is not found. (username: '" + username + "')"
                                )
                        );

    }

    private List<GrantedAuthority> toGrantedAuthorityList(UserEntity.Authority authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority.name()));
    }
}
