package com.money.api.repository.lancamento;

import com.money.api.model.Lancamento;
import com.money.api.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
