package com.dewemo.literalura.model;

import jakarta.persistence.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private Idioma idioma;
    private Double numeroDeDescargas;
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    // Constructor predeterminado
    public Libro(){}

    // Constructor
    public  Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.fromString(datosLibro.idioma().stream()
                .limit(1).collect(Collectors.joining()));
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
        this.autor = (Autor) datosLibro.autor().stream()
                .map(d -> new Autor(d, this))
                .collect(Collectors.toList());
    }

    // Getter y Setter
    public Autor getAutores() {
        return autor;
    }

    public void setAutores(Autor autor) {
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

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    // Metodo toString
    @Override
    public String toString() {
        return  "titulo =" + titulo +
                ", autor ='" + autor + '\'' +
                ", idioma ='" + idioma + '\'' +
                ", numero de descargas =" + numeroDeDescargas + '\'';
    }
}
