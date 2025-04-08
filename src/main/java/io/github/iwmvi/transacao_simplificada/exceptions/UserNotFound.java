package io.github.iwmvi.transacao_simplificada.exceptions;

public class UserNotFound extends RuntimeException {

    public UserNotFound(String mensagem) {
        super(mensagem);
    }
}
