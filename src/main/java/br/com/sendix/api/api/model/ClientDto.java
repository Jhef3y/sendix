package br.com.sendix.api.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ClientDto {
    @NotBlank
    private String externalId;
}
