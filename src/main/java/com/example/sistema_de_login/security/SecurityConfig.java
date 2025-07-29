package com.example.sistema_de_login.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configurações de segurança, como autenticação e autorização
        http.
        authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Libera acesso aos arquivos estáticos
            .requestMatchers("/login", "/register", "/register/**", "/recuperar-senha").permitAll() // Permite acesso a essas rotas sem autenticação
            .requestMatchers("/admin/**").hasRole("ADMIN") // Apenas usuários com a role ADMIN podem acessar rotas que começam com /admin
            .requestMatchers("/user/**").hasRole("USER") // Apenas usuários com a role USER podem acessar rotas que começam com /user
            .anyRequest().authenticated()
        )
            
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/dashboard", true) // Redireciona para /dashboard após login bem-sucedido   
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout") // Redireciona para /login com parâmetro de logout após logout bem-sucedido
            .permitAll()
        );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define o encoder de senha como BCrypt
    }
}