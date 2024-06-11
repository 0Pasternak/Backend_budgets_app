package com.profilerenovation.dto;

public class TranslatorDTO {
    private String nombre;
    private String textoEsp;
    private String textoEng;
    private Long id;

    public TranslatorDTO(Long id, String nombre, String textoEsp, String textoEng) {
        this.id = id;
        this.nombre = nombre;
        this.textoEsp = textoEsp;
        this.textoEng = textoEng;
    }

    public TranslatorDTO(String nombre, String textoEsp, String textoEng) {
        this.nombre = nombre;
        this.textoEsp = textoEsp;
        this.textoEng = textoEng;
    }

    public TranslatorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTextoEsp() {
        return textoEsp;
    }

    public void setTextoEsp(String textoEsp) {
        this.textoEsp = textoEsp;
    }

    public String getTextoEng() {
        return textoEng;
    }

    public void setTextoEng(String textoEng) {
        this.textoEng = textoEng;
    }
}
