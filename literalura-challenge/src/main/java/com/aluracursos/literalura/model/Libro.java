package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    private List<String> lenguajes;
    private long descargas;


    public Libro() {
    }

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.lenguajes = datosLibro.lenguajes();
        this.descargas = datosLibro.descargas();
        this.autor = autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = lenguajes;
    }

    public long getDescargas() {
        return descargas;
    }

    public void setDescargas(long descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "~°~°~°~°~°~°Libro°~°~°~°~°~°~"+ "\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Lenguajes: " + lenguajes + "\n" +
                "Descargas: " + descargas + "\n" +
                "~°~°~°~°~°~°~°~°~°~°~°~°~°~°~" + "\n";

    }
}
