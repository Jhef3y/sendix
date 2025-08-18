package br.com.sendix.api.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AppResponse {
    private UUID id;
    private String name;
    private boolean active;
}
