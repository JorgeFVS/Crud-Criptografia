package com.jorge.encrypt_api.domain.operation.exceptions;

public class OperationNotFoundException extends RuntimeException{
    public OperationNotFoundException(Long id) {
        super("Operation Not Found with ID" + id);
    }
}
