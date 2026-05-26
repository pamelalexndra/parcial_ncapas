package org.example.parcial2.dto.response;

import lombok.Data;
import org.example.parcial2.model.ArticleType;

import java.math.BigDecimal;

@Data
public class ArticleResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private ArticleType type;
    private ProviderResponse provider;
}

