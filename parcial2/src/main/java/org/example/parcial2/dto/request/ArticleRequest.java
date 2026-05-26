package org.example.parcial2.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.parcial2.model.ArticleType;

import java.math.BigDecimal;

@Data
public class ArticleRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Type is a mandatory attribute")
    private ArticleType type;

    @NotBlank(message = "Price is mandatory")
    private BigDecimal price;

    @NotBlank(message = "Provider is mandatory attribute")
    private org.example.parcial2.model.MagicProvider provider;
}



