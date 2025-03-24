package br.com.booknrest.booknrest.entities;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import org.apache.commons.lang3.StringUtils;

public class Cliente {
    private final Long id;
    private final String nome;
    private final String telefone;

    public Cliente(Long id, String nome, String telefone) {
        if (StringUtils.isBlank(nome) || StringUtils.isBlank(telefone)) {
            throw new ErroDeValidacao("Campos nome e telefone devem ser informados");
        }

        if (!telefone.matches("\\d{2} \\d{5}-\\d{4}")) {
            throw new ErroDeValidacao("Telefone deve seguir o padrão 'XX XXXXX-XXXX'");
        }

        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }
}