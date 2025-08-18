package br.com.sendix.api.domain.repository;

import br.com.sendix.api.domain.model.App;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppRepository extends JpaRepository<App, UUID> {
}
