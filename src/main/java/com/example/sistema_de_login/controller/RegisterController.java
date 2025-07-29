package com.example.sistema_de_login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sistema_de_login.model.Usuario;
import com.example.sistema_de_login.repository.UsuarioRepository;

@Controller
public class RegisterController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Retorna a view de registro
    }
    @PostMapping("/register")
    public String processRegister(@RequestParam String nome,
                                  @RequestParam String email,
                                  @RequestParam String senha,
                                  Model model) {
        // Verifica se o usuário já existe
        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("mensagem", "E-mail já cadastrado.");
            return "register"; // Retorna à página de registro com erro
        }
        // Aqui você pode adicionar a lógica para salvar o usuário no banco de dados
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(new BCryptPasswordEncoder().encode(senha));


        usuario.setRole("USER"); // Definindo um papel padrão, se necessário
        usuarioRepository.save(usuario); // Salva o usuário no repositório

        model.addAttribute("message", "Usuário registrado com sucesso!"); // Mensagem de sucesso
        // lógica de cadastro
        return "redirect:/login"; // Redireciona para a página de login após o registro
    }
}
