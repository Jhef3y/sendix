package br.com.sendix.api.domain.service;

import br.com.sendix.api.domain.exception.AppNotFoundException;
import br.com.sendix.api.domain.exception.CredentialNotFoundException;
import br.com.sendix.api.domain.model.App;
import br.com.sendix.api.domain.model.Credential;
import br.com.sendix.api.domain.repository.AppRepository;
import br.com.sendix.api.domain.repository.CredentialRepository;
import br.com.sendix.api.infra.security.SecureTokenGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final CredentialRepository credentialRepository;
    private final AppRepository appRepository;
    private final PasswordEncoder passwordEncoder;

    public record NewCredentialResult(String rawToken, Credential credential) {
    }

    @Transactional
    public NewCredentialResult createCredential(UUID appId, String credentialName) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new AppNotFoundException(appId));

        String rawToken = "sdx_live_" + SecureTokenGenerator.generateToken(32);
        String tokenHash = passwordEncoder.encode(rawToken);
        OffsetDateTime expiresAt = OffsetDateTime.now().plusYears(1);

        Credential credential = Credential.newCredential(credentialName, tokenHash, expiresAt, app);

        Credential savedCredential = credentialRepository.save(credential);

        return new NewCredentialResult(rawToken, savedCredential);
    }

    @Transactional
    public void revokeCredential(UUID credentialId) {
        if (!credentialRepository.existsById(credentialId)) {
            throw new CredentialNotFoundException(credentialId);
        }
        credentialRepository.deleteById(credentialId);
    }

    public List<Credential> listCredentialsForApp(UUID appId) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new AppNotFoundException(appId));
        return app.getCredentials();
    }}