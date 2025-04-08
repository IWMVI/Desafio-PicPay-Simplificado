package io.github.iwmvi.transacao_simplificada.services;

import org.springframework.stereotype.Service;

import io.github.iwmvi.transacao_simplificada.infrastructure.entity.Carteira;
import io.github.iwmvi.transacao_simplificada.infrastructure.repository.CarteiraRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository repository;

    public void salvar(Carteira carteira) {
        repository.save(carteira);
    }
}
