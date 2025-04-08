package io.github.iwmvi.transacao_simplificada.services;

import org.springframework.stereotype.Service;

import io.github.iwmvi.transacao_simplificada.clients.NotificacaoClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoClient client;

    public void enviarNotificacao() {
        client.enviarNotificacao();
    }
}
