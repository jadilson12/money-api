package com.money.api.resource;

import com.money.api.event.RecursoCriandoEvent;
import com.money.api.exceptionrenderer.MoneyExceptionRenderer;
import com.money.api.model.Lancamento;
import com.money.api.repository.LancamentoRepository;
import com.money.api.repository.filter.LancamentoFilter;
import com.money.api.services.LancamentoService;
import com.money.api.services.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
        return lancamentoRepository.filtar(lancamentoFilter);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarByCodigo(@PathVariable Long codigo) {
        Optional<Lancamento> lancamentoShow = lancamentoRepository.findById(codigo);
        if (lancamentoShow.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lancamentoShow.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
}
