package org.example.parcial2.controllers;

import jakarta.validation.Valid;
import org.example.parcial2.dto.request.ArticleRequest;
import org.example.parcial2.dto.response.ArticleResponse;
import org.example.parcial2.model.ArticleType;
import org.example.parcial2.services.MagicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/artefacts")
public class ArticleController {

    private final MagicService magicService;

    public ArticleController(MagicService magicService) {
        this.magicService = magicService;
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(magicService.createArticle(request));
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticles(
            @RequestParam(name = "category", required = false) ArticleType type,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(name = "provider", required = false) Long providerId) {
        return ResponseEntity.ok(magicService.getArticles(type, maxPrice, providerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(magicService.getArticleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.ok(magicService.updateArticle(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        magicService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}

