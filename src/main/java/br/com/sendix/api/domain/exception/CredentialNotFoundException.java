package br.com.sendix.api.domain.exception;

import java.util.UUID;

public class CredentialNotFoundException extends RuntimeException {
    public CredentialNotFoundException(UUID credentialId) {
        super("Não foi encontrada uma Credencial com o ID: " + credentialId);
    }
}