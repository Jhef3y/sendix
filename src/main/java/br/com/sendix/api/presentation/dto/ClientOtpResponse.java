package br.com.sendix.api.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ClientOtpResponse {
    private UUID clientId;
    private String externalId;
    private OtpResponse otpResponse;
}
