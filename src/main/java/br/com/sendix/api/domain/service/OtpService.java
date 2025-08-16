// src/main/java/br/com/sendix/api/domain/service/OtpService.java

package br.com.sendix.api.domain.service;

import br.com.sendix.api.api.model.*;
import br.com.sendix.api.domain.model.Client;
import br.com.sendix.api.domain.repository.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final ClientRepository clientRepository;
    private final QrCodeGeneratorService qrCodeGeneratorService;

    public ClientOtpDto create(ClientDto clientDto) {
        Client client = Client.newClient(clientDto.getExternalId());

        String otpUri = String.format("otpauth://totp/Sendix?secret=%s&issuer=Sendix",
                client.getSecretKey());

        String qrCodeBase64 = qrCodeGeneratorService.generateQrCodeBase64(otpUri);

        OtpDto otpDto = new OtpDto(qrCodeBase64, client.getSecretKey());

        clientRepository.saveAndFlush(client);

        return new ClientOtpDto(client.getId(), client.getExternalId(), otpDto);
    }

    public ValidateOtpResponseDto validate(@Valid ValidateOtpDto input) {
        Client client = clientRepository.findById(input.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean isValid = client.isValidCode(input.getCode());

        if (isValid) {
            return new ValidateOtpResponseDto(true, OffsetDateTime.now());
        } else {
            return new ValidateOtpResponseDto(false, OffsetDateTime.now());
        }
    }
}