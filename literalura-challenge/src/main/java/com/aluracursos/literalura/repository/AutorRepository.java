package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    //Optional<Autor> findByNombre(String name); //no estoy tan seguro
    Optional<Autor> findByNombreContainsIgnoreCase(String nombre);


    @Query("SELECT s FROM Autor s WHERE s.fechaNacimiento <= :fecha AND s.fechaMuerte >= :fecha")
    List<Autor> autorVivoEnCiertoAnio(@Param("fecha") Long fecha);
}
