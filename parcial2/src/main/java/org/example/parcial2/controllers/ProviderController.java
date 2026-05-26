package org.example.parcial2.controllers;

import jakarta.validation.Valid;
import org.example.parcial2.dto.request.ProviderRequest;
import org.example.parcial2.dto.response.ProviderResponse;
import org.example.parcial2.services.MagicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final MagicService magicService;

    public ProviderController(MagicService magicService) {
        this.magicService = magicService;
    }

    @PostMapping
    public ResponseEntity<ProviderResponse> createProvider(@Valid @RequestBody ProviderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(magicService.createProvider(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(magicService.getProviderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponse> updateProvider(@PathVariable Long id, @Valid @RequestBody ProviderRequest request) {
        return ResponseEntity.ok(magicService.updateProvider(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        magicService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}