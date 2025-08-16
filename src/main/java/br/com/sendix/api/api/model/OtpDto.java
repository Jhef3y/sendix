package br.com.sendix.api.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OtpDto {
    private String qrCode;
    private String code;
}
