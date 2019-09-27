package com.money.api.exceptionrenderer;

import net.bytebuddy.build.Plugin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class MoneyExceptionRenderer extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        String messageUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String messageDesenvolvedor = ex.getCause().toString();
        List<Erro>  erros = Arrays.asList(new Erro(messageUsuario, messageDesenvolvedor));
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Erro> erros = criarListaDeErro(ex.getBindingResult());
        return handleExceptionInternal(ex,erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> criarListaDeErro(BindingResult bindingResult) {
        List<Erro> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = fieldError.toString();
            erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        }

        return erros;
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                       WebRequest request) {
        String messageUsuario = messageSource.getMessage("resurso.nao-encontrado", null,
                LocaleContextHolder.getLocale());
        String messageDesenvolvedor = ex.toString();
        List<Erro>  erros = Arrays.asList(new Erro(messageUsuario, messageDesenvolvedor));
        return handleExceptionInternal(ex,erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
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
