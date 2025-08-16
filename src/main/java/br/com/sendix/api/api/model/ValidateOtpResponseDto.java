package br.com.sendix.api.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class ValidateOtpResponseDto {
    private boolean valid;
    private OffsetDateTime validatedIn;
}
