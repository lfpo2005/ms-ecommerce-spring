package dev.luisoliveira.msproductmanagement.exceptions;

public class DataIntegratyViolationException extends RuntimeException {
    public DataIntegratyViolationException(String msg) {
        super(msg);
    }
}
