package io.github.iwmvi.transacao_simplificada.services;

import java.math.BigDecimal;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import io.github.iwmvi.transacao_simplificada.clients.NotificacaoClient;
import io.github.iwmvi.transacao_simplificada.controller.TransacaoDTO;
import io.github.iwmvi.transacao_simplificada.infrastructure.entity.Carteira;
import io.github.iwmvi.transacao_simplificada.infrastructure.entity.TipoUsuario;
import io.github.iwmvi.transacao_simplificada.infrastructure.entity.Transacoes;
import io.github.iwmvi.transacao_simplificada.infrastructure.entity.Usuario;
import io.github.iwmvi.transacao_simplificada.infrastructure.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final UsuarioService usuarioService;
    private final CarteiraService carteiraService;
    private final AutorizacaoService autorizacaoService;
    private final TransacaoRepository transacaoRepository;
    private final NotificacaoClient notificacaoClient;

    @Transactional
    public void transferirValor(TransacaoDTO transacaoDTO) throws BadRequestException {
        Usuario pagador = usuarioService.buscarUsuario(transacaoDTO.payer());
        Usuario recebedor = usuarioService.buscarUsuario(transacaoDTO.payee());

        validarPagadorLojista(pagador);
        validarSaldoUsuario(pagador, transacaoDTO.value());
        validarTransferencia();

        pagador.getCarteira().setSaldo(pagador.getCarteira().getSaldo().subtract(transacaoDTO.value()));
        atualizarSaldo(pagador.getCarteira());
        recebedor.getCarteira().setSaldo(pagador.getCarteira().getSaldo().add(transacaoDTO.value()));
        atualizarSaldo(recebedor.getCarteira());

        Transacoes transacoes = Transacoes.builder().valor(transacaoDTO.value())
                .pagador(pagador).recebedor(recebedor)
                .build();

        transacaoRepository.save(transacoes);
        enviarNotificacao();
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

    private void atualizarSaldo(Carteira carteira) {
        carteiraService.salvar(carteira);
    }

    private void enviarNotificacao() throws BadRequestException {
        try {
            notificacaoClient.enviarNotificacao();
        } catch (HttpClientErrorException e) {
            throw new BadRequestException("Erro ao enviar notificação.");
        }
    }
}
