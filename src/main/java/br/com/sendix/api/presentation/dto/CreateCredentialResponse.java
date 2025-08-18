package br.com.sendix.api.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CreateCredentialResponse {
    private UUID id;
    private String name;
    private String rawToken;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;
}
