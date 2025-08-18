package br.com.sendix.api.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class App {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    private boolean active;

    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credential> credentials = new ArrayList<>();

    public static App newApp(String name) {
        App app = new App();
        app.id = UUID.randomUUID();
        app.name = name;
        app.active = true;
        return app;
    }

    public void deactivate() {
        this.active = false;
    }

    public void addCredential(Credential credential) {
        this.credentials.add(credential);
        credential.setApp(this);
    }
}
