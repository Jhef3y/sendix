package br.com.sendix.api.presentation.controller;

import br.com.sendix.api.domain.service.AppService;
import br.com.sendix.api.domain.model.App;
import br.com.sendix.api.presentation.dto.AppResponse;
import br.com.sendix.api.presentation.dto.CreateAppRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apps")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    @PostMapping
    public ResponseEntity<AppResponse> createApp(@RequestBody CreateAppRequest request) {
        App newApp = appService.createApp(request.getName());
        AppResponse response = new AppResponse(newApp.getId(), newApp.getName(), newApp.isActive());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{appId}")
    public ResponseEntity<AppResponse> getAppById(@PathVariable UUID appId) {
        App app = appService.findAppById(appId);
        AppResponse response = new AppResponse(app.getId(), app.getName(), app.isActive());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AppResponse>> listAllApps() {
        List<App> apps = appService.findAllApps();
        List<AppResponse> response = apps.stream()
                .map(app -> new AppResponse(app.getId(), app.getName(), app.isActive()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{appId}")
    public ResponseEntity<Void> deleteApp(@PathVariable UUID appId) {
        appService.deleteApp(appId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{appId}/deactivation")
    public ResponseEntity<AppResponse> deactivateApp(@PathVariable UUID appId) {
        App deactivatedApp = appService.deactivateApp(appId);
        AppResponse response = new AppResponse(deactivatedApp.getId(), deactivatedApp.getName(), deactivatedApp.isActive());
        return ResponseEntity.ok(response);
    }
}