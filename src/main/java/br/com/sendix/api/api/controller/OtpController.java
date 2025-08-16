package br.com.sendix.api.api.controller;

import br.com.sendix.api.api.model.ClientDto;
import br.com.sendix.api.api.model.ClientOtpDto;
import br.com.sendix.api.api.model.ValidateOtpDto;
import br.com.sendix.api.api.model.ValidateOtpResponseDto;
import br.com.sendix.api.domain.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
@Slf4j
public class OtpController {

    private final OtpService otpService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientOtpDto create(@Valid @RequestBody ClientDto input) {
        return otpService.create(input);
    }

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public ValidateOtpResponseDto validate(@Valid @RequestBody ValidateOtpDto input) {
        return otpService.validate(input);
    }
}
