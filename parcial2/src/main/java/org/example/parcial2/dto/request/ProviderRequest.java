package org.example.parcial2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.parcial2.model.ArticleType;

@Data
public class ProviderRequest {
    @NotBlank(message = "Provider name is required")
    private String name;

    @NotNull(message = "Provider type is required")
    private ArticleType type;
}
