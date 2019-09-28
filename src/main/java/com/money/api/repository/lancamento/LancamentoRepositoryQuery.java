package com.money.api.repository.lancamento;

import com.money.api.model.Lancamento;
import com.money.api.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery {

    public List<Lancamento> filtar(LancamentoFilter lancamentoFilter);
}
