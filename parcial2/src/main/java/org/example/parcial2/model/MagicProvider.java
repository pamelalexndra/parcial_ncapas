package org.example.parcial2.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "magic_providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MagicProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleType type;

    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private List<MagicArticle> articles = new ArrayList<>();
}