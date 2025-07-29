package com.example.sistema_de_login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sistema_de_login.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}