package com.money.api.services;

import com.money.api.model.Pessoa;
import com.money.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long codigo, Pessoa pessoa) {
        Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);

        if (pessoaSalva.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        pessoa.setCodigo(codigo);
        return pessoaRepository.save(pessoa);
    }
}
