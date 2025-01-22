package com.dewemo.literalura.principal;

import com.dewemo.literalura.model.*;
import com.dewemo.literalura.repository.LibroRepository;
import com.dewemo.literalura.service.ConsumoAPI;
import com.dewemo.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository libroRepository;
    public Principal(LibroRepository libroRepository ) {
        this.libroRepository = libroRepository;
        }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
                    ▓               **MENU PRINCIPAL**                 ▓
                    ▓                                                  ▓
                    ▓ 1 - Buscar Libro por Titulo                      ▓
                    ▓ 2 - Listar Libros Registrados                    ▓
                    ▓ 3 - Listar Autores Registrados                   ▓
                    ▓ 4 - Listar Autores Vivos en un Determinado Año   ▓
                    ▓ 5 - Listar Libros por Idioma                     ▓
                    ▓                                                  ▓
                    ▓ 0 - Salir                                        ▓
                    ▓                                                  ▓
                    ▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓
                    ▓ Digite Opción                                    ▓
                    ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroEnLaApi();
                    break;
//
//                case 2:
//                   listarLibrosRegistrados();
//                    break;
//                case 3:
//                    listarAutoresRegistrados();
//                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro Encontrado");
            return libroBuscado.get();
        } else {
            System.out.println("Libro NO encontrado");
            return null;
        }
    }

    // Guardar los libros encontrados en la API en la base de datos.
    private void buscarLibroEnLaApi() {
        DatosLibro datos = getDatosLibro();
        //DatosAutor datosAutor = getDatosLibro();
        System.out.println(datos);

        var titulo = datos.titulo();
        var buscarLibro =  libroRepository.findByTituloContainsIgnoreCase(titulo);
        System.out.println(buscarLibro);

        if(buscarLibro.isPresent()){
            System.out.println("El libro ya se encuentra en la base de datos");
        } else {
            Libro libro = new Libro();
            Autor autor = new Autor();
            libroRepository.save(libro);
            System.out.println("Se guardó satisfactoriamente el libro en la base de datos");
            System.out.println("DATOS LIBROS: " + datos);
        }
    }
}