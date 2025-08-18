package br.com.sendix.api.domain.exception;

import java.util.UUID;

public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException(UUID appId) {
        super("Não foi encontrado um App com o ID: " + appId);
    }
}