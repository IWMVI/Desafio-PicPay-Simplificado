package io.github.iwmvi.transacao_simplificada.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "https://util.devi.tools/api/v1/notify", name = "notificacao")
public interface NotificacaoClient {

    @PostMapping
    void enviarNotificacao();

}
