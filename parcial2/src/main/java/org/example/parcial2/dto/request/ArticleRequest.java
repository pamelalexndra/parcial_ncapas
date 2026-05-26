package org.example.parcial2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.parcial2.model.ArticleType;

import java.math.BigDecimal;

@Data
public class ArticleRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Type is a mandatory attribute")
    private ArticleType type;

    @NotNull(message = "Price is mandatory")
    private BigDecimal price;

    @NotNull(message = "Provider is mandatory attribute")
    private Long providerId;
}



