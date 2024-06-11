package com.profilerenovation.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profilerenovation.dto.TranslatorDTO;
import com.profilerenovation.service.ITranslator;

import java.io.Writer;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.FileOutputStream;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/pageController")
public class TranslatorController {

    @Autowired
    private ITranslator translatorService;

    @GetMapping("/getImg")
    @PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
    public List<TranslatorDTO> getImg(@RequestParam String query) {
        return translatorService.getTraduccionesByNombre("img_" + query);
    }

    @GetMapping("/getText")
    @PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
    public List<TranslatorDTO> getText(@RequestParam String query) {
        return translatorService.getTraduccionesByNombre("text_" + query);
    }

    @PutMapping("/updateImg")
    @PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
    public TranslatorDTO updateImg(@RequestParam Long id, @RequestBody TranslatorDTO translatorDTO) {
        return translatorService.updateTraduccion(id, translatorDTO);
    }

    @GetMapping("/translator")
    @PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
    public Map<String, Map<String, String>> getTraducciones() {
        List<TranslatorDTO> traducciones = translatorService.getAllTraducciones();

        Map<String, String> esJson = new HashMap<>();
        Map<String, String> enJson = new HashMap<>();

        for (TranslatorDTO traduccion : traducciones) {
            esJson.put(traduccion.getNombre(), traduccion.getTextoEsp());
            enJson.put(traduccion.getNombre(), traduccion.getTextoEng());
        }

        Map<String, Map<String, String>> result = new HashMap<>();
        result.put("es", esJson);
        result.put("en", enJson);

        // Guardar los JSONs en el sistema de archivos
        saveTranslationsToFile(result);

        return result;
    }

    private void saveTranslationsToFile(Map<String, Map<String, String>> translations) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Guardar el archivo es.json
            String esPath = Paths.get("C:/Users/paste/Desktop/personal-profile-reformas/src/i18n/es.json").toString();
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(new File(esPath)),
                    StandardCharsets.UTF_8)) {
                mapper.writeValue(writer, translations.get("es"));
            }

            // Guardar el archivo en.json
            String enPath = Paths.get("C:/Users/paste/Desktop/personal-profile-reformas/src/i18n/en.json").toString();
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(new File(enPath)),
                    StandardCharsets.UTF_8)) {
                mapper.writeValue(writer, translations.get("en"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}