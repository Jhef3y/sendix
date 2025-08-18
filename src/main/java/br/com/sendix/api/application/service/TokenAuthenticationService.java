package br.com.sendix.api.application.service;

import br.com.sendix.api.domain.model.App;
import br.com.sendix.api.domain.model.Credential;
import br.com.sendix.api.domain.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationService {

    private final AppRepository appRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<App> authenticate(String rawToken, UUID appId) {
        if (rawToken == null || rawToken.isBlank() || appId == null) {
            return Optional.empty();
        }

        Optional<App> appOptional = appRepository.findById(appId);

        if (appOptional.isEmpty()) {
            return Optional.empty();
        }

        App app = appOptional.get();

        for (Credential credential : app.getCredentials()) {
            if (passwordEncoder.matches(rawToken, credential.getToken())) {

                boolean isExpired = credential.getExpiresAt().isBefore(OffsetDateTime.now());
                boolean isAppActive = app.isActive();

                if (!isExpired && isAppActive) {
                    return Optional.of(app);
                } else {
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }
}