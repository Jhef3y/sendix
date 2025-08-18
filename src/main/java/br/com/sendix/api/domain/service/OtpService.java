package br.com.sendix.api.domain.service;

import br.com.sendix.api.application.service.QrCodeGeneratorService;
import br.com.sendix.api.domain.model.App;
import br.com.sendix.api.domain.model.Client;
import br.com.sendix.api.domain.repository.AppRepository;
import br.com.sendix.api.domain.repository.ClientRepository;
import br.com.sendix.api.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final ClientRepository clientRepository;
    private final QrCodeGeneratorService qrCodeGeneratorService;
    private final AppRepository appRepository;

    public ClientOtpResponse create(ClientRequest clientRequest, UUID appId) {
        Client client = Client.newClient(clientRequest.getExternalId());

        App app = appRepository.findById(appId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "App not found"));

        String otpUri = String.format("otpauth://totp/%s?secret=%s",
                app.getName(), client.getSecretKey());

        String qrCodeBase64 = qrCodeGeneratorService.generateQrCodeBase64(otpUri);

        OtpResponse otpResponse = new OtpResponse(qrCodeBase64, client.getSecretKey());

        clientRepository.saveAndFlush(client);

        return new ClientOtpResponse(client.getId(), client.getExternalId(), otpResponse);
    }

    public ValidateOtpResponse validate(@Valid ValidateOtpRequest input) {
        Client client = clientRepository.findById(input.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean isValid = client.isValidCode(input.getCode());

        if (isValid) {
            return new ValidateOtpResponse(true, OffsetDateTime.now());
        } else {
            return new ValidateOtpResponse(false, OffsetDateTime.now());
        }
    }
}