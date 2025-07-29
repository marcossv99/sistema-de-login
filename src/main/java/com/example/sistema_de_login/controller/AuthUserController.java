package com.example.sistema_de_login.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthUserController {
    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna a view de login
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/dashboard")
    public String userDashboard(){
        return "user_dashboard"; // Retorna a view do dashboard do usuário
    }
}
