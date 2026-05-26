package org.example.parcial2.repositories;

import org.example.parcial2.model.MagicProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagicProviderRepository extends JpaRepository<MagicProvider, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}