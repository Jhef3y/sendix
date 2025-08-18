package br.com.sendix.api.presentation.controller;

import br.com.sendix.api.presentation.dto.ClientRequest;
import br.com.sendix.api.presentation.dto.ClientOtpResponse;
import br.com.sendix.api.presentation.dto.ValidateOtpRequest;
import br.com.sendix.api.presentation.dto.ValidateOtpResponse;
import br.com.sendix.api.domain.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
@Slf4j
public class OtpController {

    private final OtpService otpService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientOtpResponse create(@Valid @RequestBody ClientRequest input, @RequestHeader(value = "X-App-ID") UUID appId) {
        return otpService.create(input, appId);
    }

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public ValidateOtpResponse validate(@Valid @RequestBody ValidateOtpRequest input) {
        return otpService.validate(input);
    }
}
