package io.github.iwmvi.transacao_simplificada.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

import io.github.iwmvi.transacao_simplificada.clients.AutorizacaoClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorizacaoService {

    private final AutorizacaoClient client;

    public boolean valTransferencia() {
        if (Objects.equals(client.valAutorizacaoDTO().data().authorization(), true))
            return true;
        else
            return false;
    }
}
