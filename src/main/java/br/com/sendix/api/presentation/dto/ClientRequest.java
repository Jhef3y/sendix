package br.com.sendix.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ClientRequest {
    @NotBlank
    private String externalId;
}
