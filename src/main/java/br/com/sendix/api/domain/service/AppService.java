package br.com.sendix.api.domain.service;

import br.com.sendix.api.domain.exception.AppNotFoundException;
import br.com.sendix.api.domain.model.App;
import br.com.sendix.api.domain.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;

    @Transactional
    public App createApp(String name) {
        App newApp = App.newApp(name);
        return appRepository.save(newApp);
    }

    public App findAppById(UUID appId) {
        return appRepository.findById(appId)
                .orElseThrow(() -> new AppNotFoundException(appId));
    }

    public List<App> findAllApps() {
        return appRepository.findAll();
    }

    @Transactional
    public App deactivateApp(UUID appId) {
        App app = findAppById(appId);
        app.deactivate();
        return appRepository.save(app);
    }

    @Transactional
    public void deleteApp(UUID appId) {
        if (!appRepository.existsById(appId)) {
            throw new AppNotFoundException(appId);
        }
        appRepository.deleteById(appId);
    }
}