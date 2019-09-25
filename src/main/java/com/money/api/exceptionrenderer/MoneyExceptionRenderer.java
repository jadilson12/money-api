package com.money.api.exceptionrenderer;

import net.bytebuddy.build.Plugin;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MoneyExceptionRenderer extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
          HttpHeaders headers, HttpStatus status, WebRequest request) {
        String messageUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String messageDesenvolvedor = ex.getCause().toString();
        return handleExceptionInternal(ex, new Erro(messageUsuario, messageDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request);
    }

    public static class Erro {

        private  String  menssagemUsuario;
        private  String messageDesenvolvedor;

        Erro(String menssagemUsuario, String messageDesenvolvedor) {
            this.menssagemUsuario = menssagemUsuario;
            this.messageDesenvolvedor = messageDesenvolvedor;
        }

        public String getMenssagemUsuario() {
            return menssagemUsuario;
        }

        public void setMenssagemUsuario(String menssagemUsuario) {
            this.menssagemUsuario = menssagemUsuario;
        }

        public String getMessageDesenvolvedor() {
            return messageDesenvolvedor;
        }

        public void setMessageDesenvolvedor(String messageDesenvolvedor) {
            this.messageDesenvolvedor = messageDesenvolvedor;
        }
    }
}
