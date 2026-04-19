package com.portfolio.devicemanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // H2-console用の設定（Spring Boot 3系の書き方）
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // H2コンソールを許可
                        .requestMatchers("/login", "/webjars/**", "/css/**", "/js/**").permitAll() // ログイン画面や静的ファイルは全員許可
                        .requestMatchers("/user/**", "/admin/**").hasAuthority("ADMIN") // 管理者専用
                        .anyRequest().authenticated() //それ以外はログインが必要
                )
                // h2-console用CSRFを無効化設定
                .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                // h2-consoleを表示するためにFrameOptionsを無効化
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // ログインの設定
                .formLogin(login -> login
                        .loginPage("/login") // 自作ログイン画面
                                .defaultSuccessUrl("/", true) // ログイン成功時の遷移先
                                .permitAll()
                )

                // ログアウトの設定
                .logout(logout ->logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 最近のSpring Securityで引数なしのコンストラクタが非推奨なため
        // デフォルト設定でインスタンス化
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
