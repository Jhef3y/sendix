package br.com.sendix.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ValidateOtpRequest {
    private UUID clientId;
    @NotBlank
    private String code;
}
