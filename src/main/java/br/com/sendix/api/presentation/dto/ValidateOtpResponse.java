package br.com.sendix.api.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class ValidateOtpResponse {
    private boolean valid;
    private OffsetDateTime validatedIn;
}
