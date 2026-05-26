package org.example.parcial2.dto.response;

import lombok.Data;
import org.example.parcial2.model.ArticleType;

@Data
public class ProviderResponse {
    private Long id;
    private String name;
    private ArticleType type;
}