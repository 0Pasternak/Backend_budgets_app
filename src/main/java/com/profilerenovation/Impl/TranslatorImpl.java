package com.profilerenovation.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profilerenovation.dto.TranslatorDTO;
import com.profilerenovation.entity.Translator;
import com.profilerenovation.repository.TranslatorRepository;
import com.profilerenovation.service.ITranslator;

@Service
public class TranslatorImpl implements ITranslator {

    @Autowired
    private TranslatorRepository translatorRepository;

    @Override
    public List<TranslatorDTO> getAllTraducciones() {
        List<Translator> traducciones = translatorRepository.findAll();
        return traducciones.stream().map(traduccion -> new TranslatorDTO(
                traduccion.getNombre(),
                traduccion.getTextoEsp(),
                traduccion.getTextoEng())).collect(Collectors.toList());
    }

    @Override
    public List<TranslatorDTO> getTraduccionesByNombre(String nombre) {
        List<Translator> traducciones = translatorRepository.findByNombreContaining(nombre);
        return traducciones.stream().map(traduccion -> new TranslatorDTO(
                traduccion.getId(),
                traduccion.getNombre(),
                traduccion.getTextoEsp(),
                traduccion.getTextoEng())).collect(Collectors.toList());
    }

    @Override
    public TranslatorDTO updateTraduccion(Long id, TranslatorDTO translatorDTO) {
        Translator traduccion = translatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Translation not found"));
        traduccion.setTextoEsp(translatorDTO.getTextoEsp());
        traduccion.setTextoEng(translatorDTO.getTextoEng());
        Translator updatedTraduccion = translatorRepository.save(traduccion);
        return new TranslatorDTO(updatedTraduccion.getNombre(), updatedTraduccion.getTextoEsp(),
                updatedTraduccion.getTextoEng());
    }
}
