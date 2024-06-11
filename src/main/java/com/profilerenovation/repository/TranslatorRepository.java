package com.profilerenovation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.profilerenovation.entity.Translator;

public interface TranslatorRepository extends JpaRepository<Translator, Long> {
    @Query("SELECT t FROM Translator t WHERE t.nombre LIKE %:nombre%")
    List<Translator> findByNombreContaining(String nombre);

}
