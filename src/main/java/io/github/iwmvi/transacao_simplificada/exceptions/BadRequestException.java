package io.github.iwmvi.transacao_simplificada.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String mensagem) {
        super(mensagem);
    }

}
