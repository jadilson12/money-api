package com.money.api.services;

import com.money.api.model.Lancamento;
import com.money.api.model.Pessoa;
import com.money.api.repository.LancamentoRepository;
import com.money.api.services.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento) {
        Pessoa pessoa = pessoaService.buscarPessoaPeloCodigo(lancamento.getPessoa().getCodigo());
        if (pessoa == null || pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
    }
}
