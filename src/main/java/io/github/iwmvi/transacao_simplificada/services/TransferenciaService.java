package io.github.iwmvi.transacao_simplificada.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import io.github.iwmvi.transacao_simplificada.controller.TransacaoDTO;
import io.github.iwmvi.transacao_simplificada.infrastructure.entity.TipoUsuario;
import io.github.iwmvi.transacao_simplificada.infrastructure.entity.Usuario;
import io.github.iwmvi.transacao_simplificada.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final UsuarioService usuarioService;
    private final AutorizacaoService autorizacaoService;

    public void transferirValor(TransacaoDTO transacaoDTO) {
        Usuario pagador = usuarioService.buscarUsuario(transacaoDTO.payer());
        Usuario recebedor = usuarioService.buscarUsuario(transacaoDTO.payee());

        validarPagadorLojista(pagador);
        validarSaldoUsuario(pagador, transacaoDTO.value());
        validarTransferencia();
    }

    private void validarPagadorLojista(Usuario usuario) {
        try {
            if (usuario.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
                throw new IllegalArgumentException("Transação não autorizada para esse tipo de usuário");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validarSaldoUsuario(Usuario usuario, BigDecimal valor) {
        try {
            if (usuario.getCarteira().getSaldo().compareTo(valor) < 0) {
                throw new IllegalArgumentException("Transação não autorizada, saldo insuficiente");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validarTransferencia() {
        try {
            if (!autorizacaoService.valTransferencia()) {
                throw new IllegalArgumentException("Transação não autorizada, pela API");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
