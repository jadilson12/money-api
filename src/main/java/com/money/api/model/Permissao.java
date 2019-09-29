package com.money.api.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "permissao")
public class Permissao {

    @Id
    private Long codigo;

    private String descricao ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permissao permissao = (Permissao) o;
        return Objects.equals(codigo, permissao.codigo) &&
                Objects.equals(descricao, permissao.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, descricao);
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
