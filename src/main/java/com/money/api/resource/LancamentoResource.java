package com.money.api.resource;

import com.money.api.event.RecursoCriandoEvent;
import com.money.api.exceptionrenderer.MoneyExceptionRenderer;
import com.money.api.model.Lancamento;
import com.money.api.repository.LancamentoRepository;
import com.money.api.repository.filter.LancamentoFilter;
import com.money.api.repository.projection.ResumoLancamento;
import com.money.api.services.LancamentoService;
import com.money.api.services.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and  #oauth2.hasScope('read')")
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.resumir(lancamentoFilter, pageable);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and  #oauth2.hasScope('read')")
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.filtar(lancamentoFilter, pageable);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and  #oauth2.hasScope('read')")
    public ResponseEntity<Lancamento> buscarByCodigo(@PathVariable Long codigo) {
        Lancamento lancamentoShow = lancamentoRepository.findOne(codigo);
        if (lancamentoShow != null) {
            return ResponseEntity.ok(lancamentoShow);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and  #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalva = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new RecursoCriandoEvent(this, response, lancamentoSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> hanldePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
        String messageUsuario = messageSource.getMessage("resurso.inexistente-ou-inativa", null,
                LocaleContextHolder.getLocale());
        String messageDesenvolvedor = ex.toString();
        List<MoneyExceptionRenderer.Erro> erros = Arrays.asList(new MoneyExceptionRenderer.Erro(messageUsuario, messageDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and  #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> remover(@PathVariable @RequestBody Long codigo) {
        lancamentoRepository.delete(codigo);
        return ResponseEntity.noContent().build();
    }
}
