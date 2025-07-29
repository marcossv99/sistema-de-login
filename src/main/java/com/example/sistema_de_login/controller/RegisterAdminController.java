package com.example.sistema_de_login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sistema_de_login.model.Usuario;
import com.example.sistema_de_login.repository.UsuarioRepository;

@Controller
@PreAuthorize("hasRole('ADMIN')") // Apenas usuários com papel ADMIN podem acessar este controlador
@RequestMapping("/admin")
public class RegisterAdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "admin_register"; // Retorna a view de registro
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            Model model) {
        // Verifica se o usuário já existe
        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("mensagem", "E-mail já cadastrado.");
            return "admin_register"; // Retorna à página de registro com erro
        }
        // Aqui você pode adicionar a lógica para salvar o usuário no banco de dados
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
        usuario.setRole("ADMIN"); // Definindo um papel padrão, se necessário
        usuarioRepository.save(usuario); // Salva o usuário no repositório

        model.addAttribute("message", "Usuário registrado com sucesso!"); // Mensagem de sucesso
        // lógica de cadastro
        return "redirect:/admin_dashboard"; // Redireciona para a página de login após o registro
    }
}
