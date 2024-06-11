package com.profilerenovation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.profilerenovation.dto.TranslatorDTO;

@Service
public interface ITranslator {
    List<TranslatorDTO> getAllTraducciones();

    List<TranslatorDTO> getTraduccionesByNombre(String nombre);

    TranslatorDTO updateTraduccion(Long id, TranslatorDTO translatorDTO);
}
