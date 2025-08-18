package br.com.sendix.api.presentation.controller;

import br.com.sendix.api.domain.service.CredentialService;
import br.com.sendix.api.domain.model.Credential;
import br.com.sendix.api.presentation.dto.CreateCredentialRequest;
import br.com.sendix.api.presentation.dto.CreateCredentialResponse;
import br.com.sendix.api.presentation.dto.CredentialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apps/{appId}/credentials")
@RequiredArgsConstructor
public class CredentialController {

    private final CredentialService credentialService;

    @PostMapping
    public ResponseEntity<CreateCredentialResponse> createCredential(
            @PathVariable UUID appId,
            @RequestBody CreateCredentialRequest request) {

        CredentialService.NewCredentialResult result = credentialService.createCredential(appId, request.getName());

        CreateCredentialResponse response = new CreateCredentialResponse(
                result.credential().getId(),
                result.credential().getName(),
                result.rawToken(),
                result.credential().getCreatedAt(),
                result.credential().getExpiresAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CredentialResponse>> listCredentialsForApp(@PathVariable UUID appId) {
        List<Credential> credentials = credentialService.listCredentialsForApp(appId);

        List<CredentialResponse> response = credentials.stream()
                .map(cred -> new CredentialResponse(
                        cred.getId(),
                        cred.getName(),
                        cred.getCreatedAt(),
                        cred.getExpiresAt()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{credentialId}")
    public ResponseEntity<Void> revokeCredential(
            @PathVariable UUID appId,
            @PathVariable UUID credentialId) {
        credentialService.revokeCredential(credentialId);
        return ResponseEntity.noContent().build();
    }
}