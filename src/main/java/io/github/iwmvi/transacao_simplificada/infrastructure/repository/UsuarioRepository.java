package io.github.iwmvi.transacao_simplificada.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.iwmvi.transacao_simplificada.infrastructure.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
