package org.example.parcial2.services;

import org.example.parcial2.dto.request.ArticleRequest;
import org.example.parcial2.dto.request.ProviderRequest;
import org.example.parcial2.dto.response.ArticleResponse;
import org.example.parcial2.dto.response.ProviderResponse;
import org.example.parcial2.exceptions.ConflictException;
import org.example.parcial2.exceptions.EntityNotFoundException;
import org.example.parcial2.exceptions.UnprocessableEntityException;
import org.example.parcial2.model.ArticleType;
import org.example.parcial2.model.MagicArticle;
import org.example.parcial2.model.MagicProvider;
import org.example.parcial2.repositories.MagicArticleRepository;
import org.example.parcial2.repositories.MagicProviderRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MagicService {

    private final MagicProviderRepository providerRepository;
    private final MagicArticleRepository articleRepository;

    public MagicService(MagicProviderRepository providerRepository, MagicArticleRepository articleRepository) {
        this.providerRepository = providerRepository;
        this.articleRepository = articleRepository;
    }

    public ProviderResponse createProvider(ProviderRequest request) {
        if (providerRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Provider guild name already exists: " + request.getName());
        }
        MagicProvider provider = new MagicProvider();
        provider.setName(request.getName().trim());
        provider.setType(request.getType());
        return toProviderResponse(providerRepository.save(provider));
    }

    public ProviderResponse getProviderById(Long id) {
        MagicProvider p = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Provider not found with ID: " + id));
        return toProviderResponse(p);
    }

    public ProviderResponse updateProvider(Long id, ProviderRequest request) {
        MagicProvider existing = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Provider not found with ID: " + id));

        if (providerRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new ConflictException("Provider name already in use by another guild");
        }

        existing.setName(request.getName().trim());
        existing.setType(request.getType());
        return toProviderResponse(providerRepository.save(existing));
    }

    public void deleteProvider(Long id) {
        MagicProvider provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Provider not found with ID: " + id));

        if (provider.getArticles() != null && !provider.getArticles().isEmpty()) {
            throw new ConflictException("Cannot delete provider: Guild currently has " + provider.getArticles().size() + " active articles associated");
        }
        providerRepository.delete(provider);
    }

    public ArticleResponse createArticle(ArticleRequest request) {
        if (articleRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Article name already registered: " + request.getName());
        }

        MagicProvider provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new EntityNotFoundException("Assigned provider ID does not exist: " + request.getProviderId()));

        if (request.getType() != provider.getType()) {
            throw new UnprocessableEntityException("Business rule violation: A provider of type " + provider.getType() + " cannot supply articles of category " + request.getType());
        }

        MagicArticle article = new MagicArticle();
        article.setName(request.getName().trim());
        article.setType(request.getType());
        article.setPrice(request.getPrice());
        article.setProvider(provider);

        return toArticleResponse(articleRepository.save(article));
    }

    public List<ArticleResponse> getArticles(ArticleType type, BigDecimal maxPrice, Long providerId) {
        return articleRepository.findArticlesWithFilters(type, maxPrice, providerId).stream()
                .map(this::toArticleResponse)
                .collect(Collectors.toList());
    }

    public ArticleResponse getArticleById(Long id) {
        MagicArticle article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + id));
        return toArticleResponse(article);
    }

    public ArticleResponse updateArticle(Long id, ArticleRequest request) {
        MagicArticle existing = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + id));

        if (articleRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new ConflictException("Article name already in use by another artefact");
        }

        MagicProvider provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new EntityNotFoundException("Provider not found with ID: " + request.getProviderId()));

        if (request.getType() != provider.getType()) {
            throw new UnprocessableEntityException("Business rule violation: Selected provider type mismatch with article category");
        }

        existing.setName(request.getName().trim());
        existing.setType(request.getType());
        existing.setPrice(request.getPrice());
        existing.setProvider(provider);

        return toArticleResponse(articleRepository.save(existing));
    }

    public void deleteArticle(Long id) {
        MagicArticle article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + id));
        articleRepository.delete(article);
    }

    private ProviderResponse toProviderResponse(MagicProvider p) {
        ProviderResponse resp = new ProviderResponse();
        resp.setId(p.getId());
        resp.setName(p.getName());
        resp.setType(p.getType());
        return resp;
    }

    private ArticleResponse toArticleResponse(MagicArticle a) {
        ArticleResponse resp = new ArticleResponse();
        resp.setId(a.getId());
        resp.setName(a.getName());
        resp.setType(a.getType());
        resp.setPrice(a.getPrice());
        resp.setProvider(toProviderResponse(a.getProvider()));
        return resp;
    }
}