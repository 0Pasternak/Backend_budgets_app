package com.profilerenovation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "t_traduccion")
public class Translator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String textoEsp;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String textoEng;

    public Translator() {
        super();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
