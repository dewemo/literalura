package com.dewemo.literalura.repository;

import com.dewemo.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    //Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutor);

}
