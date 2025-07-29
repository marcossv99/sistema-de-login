
package com.example.sistema_de_login.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthAdminController {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard"; // Retorna a view do dashboard do administrador
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "admin_dashboard"; // Redireciona para o dashboard do administrador
        }
        return "user_dashboard"; // Retorna a view do dashboard genérico
    }
}
