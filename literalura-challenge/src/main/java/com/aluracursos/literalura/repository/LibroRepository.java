package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);

//    @Query("SELECT l FROM Libro l WHERE l.lenguajes = :idioma")
//    List<Libro> findByLenguajes(String idioma);


   // List<Libro> findByLenguajes(String idioma);

//    List<Libro> findByLenguajesContainingIgnoreCase(String lenguaje);

//    @Query("SELECT l FROM Libro l WHERE :lenguaje MEMBER OF l.lenguajes")
//    List<Libro> findByLenguajes(@Param("lenguaje") String lenguaje);

//    @Query("SELECT l FROM Libro l WHERE :lenguaje IN l.lenguajes")
//    List<Libro> findByLenguajes(String lenguaje);
}
