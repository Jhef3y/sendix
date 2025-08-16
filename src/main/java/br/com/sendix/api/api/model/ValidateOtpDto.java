package br.com.sendix.api.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ValidateOtpDto {
    private UUID clientId;
    @NotBlank
    private String code;
}
