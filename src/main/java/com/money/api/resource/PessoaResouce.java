package com.money.api.resource;

import com.money.api.event.RecursoCriandoEvent;
import com.money.api.model.Categoria;
import com.money.api.model.Pessoa;
import com.money.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResouce {

    @Autowired
    private PessoaRepository pessoaReposytory;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Pessoa> lista() {
        return pessoaReposytory.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = pessoaReposytory.save(pessoa);
        publisher.publishEvent(new RecursoCriandoEvent(this, response, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);

    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscarByCodigo (@PathVariable  Long codigo) {
        Optional<Pessoa> pessoaAtualiza = pessoaReposytory.findById(codigo);
        if (pessoaAtualiza.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(pessoaAtualiza.get());
    }

}