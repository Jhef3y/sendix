package br.com.sendix.api.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ClientOtpDto {
    private UUID clientId;
    private String externalId;
    private OtpDto otpDto;
}
