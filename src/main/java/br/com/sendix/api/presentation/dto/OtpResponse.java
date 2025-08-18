package br.com.sendix.api.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OtpResponse {
    private String qrCode;
    private String code;
}
