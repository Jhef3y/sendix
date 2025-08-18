package br.com.sendix.api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Credential {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String token;

    private OffsetDateTime createdAt;

    private OffsetDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private App app;

    public static Credential newCredential(String name, String token, OffsetDateTime expiresAt, App app) {
        Credential credential = new Credential();
        credential.id = UUID.randomUUID();
        credential.name = name;
        credential.token = token;
        credential.createdAt = OffsetDateTime.now();
        credential.expiresAt = expiresAt;
        credential.app = app;
        return credential;
    }
}
