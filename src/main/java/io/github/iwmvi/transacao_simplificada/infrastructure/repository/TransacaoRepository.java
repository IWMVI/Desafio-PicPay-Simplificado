package io.github.iwmvi.transacao_simplificada.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.iwmvi.transacao_simplificada.infrastructure.entity.Transacoes;

public interface TransacaoRepository extends JpaRepository<Transacoes, Long> {

}
