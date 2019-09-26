package com.money.api.event.listener;

import com.money.api.event.RecursoCriandoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriandoEvent> {

    @Override
    public void onApplicationEvent(RecursoCriandoEvent recursoCriandoEvent) {
        HttpServletResponse response = recursoCriandoEvent.getResponse();
        Long codigo = recursoCriandoEvent.getCodigo();

        adicionarHeaderLocation(response, codigo);

    }

    private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("location", uri.toASCIIString());
    }
}
