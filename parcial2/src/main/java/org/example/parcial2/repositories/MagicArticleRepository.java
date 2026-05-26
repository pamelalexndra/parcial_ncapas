package org.example.parcial2.repositories;

import org.example.parcial2.model.ArticleType;
import org.example.parcial2.model.MagicArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MagicArticleRepository extends JpaRepository<MagicArticle, Long> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    @Query("SELECT a FROM MagicArticle a WHERE " +
            "(:type IS NULL OR a.type = :type) AND " +
            "(:maxPrice IS NULL OR a.price <= :maxPrice) AND " +
            "(:providerId IS NULL OR a.provider.id = :providerId)")
    List<MagicArticle> findArticlesWithFilters(
            @Param("type") ArticleType type,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("providerId") Long providerId);
}


