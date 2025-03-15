package br.com.booknrest.booknrest.exceptions;

public class ErroDeValidacao extends RuntimeException {
    public ErroDeValidacao(String message) {
        super(message);
    }
}
