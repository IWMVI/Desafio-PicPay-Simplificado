package io.github.iwmvi.transacao_simplificada.services;

import org.springframework.stereotype.Service;

import io.github.iwmvi.transacao_simplificada.exceptions.UserNotFound;
import io.github.iwmvi.transacao_simplificada.infrastructure.entity.Usuario;
import io.github.iwmvi.transacao_simplificada.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario buscarUsuario(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFound("Usuário não encontrado."));
    }
}
