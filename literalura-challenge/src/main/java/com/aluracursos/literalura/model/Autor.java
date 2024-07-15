package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    private String nombre;
    @Column(nullable = true)
    private Long fechaNacimiento;
    @Column(nullable = true)
    private Long fechaMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
        this.nombre = "Anonymous";
        this.fechaNacimiento = null;
        this.fechaMuerte = null;
    }


    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaMuerte = datosAutor.fechaMuerte();

    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Long fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }


    @Override
    public String toString() {
        return "~°~°~°~°~°~°Autor°~°~°~°~°~°~" + "\n" +
                "Nombre: " + nombre + "\n" +
                "FechaNacimiento: " + fechaNacimiento + "\n" +
                "FechaMuerte: " + fechaMuerte + "\n" +
                "Libros: " + libros.stream().map(Libro::getTitulo).collect(Collectors.toUnmodifiableList())
                + "\n" +"~°~°~°~°~°~°~°~°~°~°~°~°~°~°~" + "\n";


    }
}
