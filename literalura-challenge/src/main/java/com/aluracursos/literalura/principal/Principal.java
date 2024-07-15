package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Optional<Libro> libroOptional;
    private Optional<Autor> autorOptional;
    private LibroRepository repository1;
    private AutorRepository repository2;
    private Scanner scanner = new Scanner(System.in);
    private List<Libro> libros;
    private List<Autor> autores;





    public Principal(LibroRepository repository1, AutorRepository repository2) {
        this.repository1 = repository1;
        this.repository2 = repository2;
    }

    public void muestraMenu(){

        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    1- Buscar Libros
                    2- Mostrar libros listados
                    3- Mostrar Autores registrados
                    4- Mostrar Autores vivos en cierto año
                    5- Listar libros por idioma
                                        
                    0- Salir
                    """;

            System.out.println(menu);

            try {

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;

                    case 2:
                        mostrarLibrosListados();
                        break;

                    case 3:
                        mostrarAutoresListados();
                        break;

                    case 4:
                        autoresVivosPorAnio();
                        break;

                    case 5:
                        librosPorIdioma();
                        break;


                    case 0:
                        System.out.println("Cerrando la aplicacion");
                        break;

                    default:
                        System.out.println("Opcion invalida");
                        break;

                }
            }catch (InputMismatchException e){
                System.out.println("Eso nisiquiera es un numero");
                scanner.nextLine();
            }
        }



    }


  private DatosLibro getDatosLibro(){
        System.out.println("Que libro deseas buscar?");
        var nombreLibro = scanner.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE
        + "search=" + nombreLibro.replace(" ", "%20"));
        System.out.println(json);
        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);
        if(datos.results().isEmpty()){
            return null;
        }else {
            return datos.results().get(0);
        }

    }

    private  Autor obtenerAutor(DatosLibro datosLibro){
        List<DatosAutor> datosautores = datosLibro.autor();
        if (!datosautores.isEmpty()){
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Autor autor = new Autor(datosAutor);
            System.out.println("Autor: "+ autor.getNombre());
            return autor;
        }else {
            Autor autor = new Autor();
            System.out.println("Autor: " + autor.getNombre());
            return autor;
        }
    }

    private void guardadAutor(Autor autor){
        autorOptional = repository2.findByNombreContainsIgnoreCase(autor.getNombre());
        if(autorOptional.isPresent()){
        }else{
            repository2.save(autor);
        }
    }


    private void buscarLibro(){
        DatosLibro datosLibro = getDatosLibro();//creamos la plantilla de datos para maniobrar
        if(datosLibro != null){

            libroOptional = repository1.findByTitulo(datosLibro.titulo()); //traemos info de la base de datos
            if (libroOptional.isPresent()){
                System.out.println("Libro existente no hay necesidad de registrar");
            }else {
                Autor autor = obtenerAutor(datosLibro);
                guardadAutor(autor);
                Libro libro = new Libro(datosLibro, autor);
                System.out.println(libro);
                repository1.save(libro);
            }

        }else{
            System.out.println("libro no encontrado");
        }
    }

    private void mostrarLibrosListados() {
        libros = repository1.findAll();
        if(libros.isEmpty()){
            System.out.println("Aun no hay libros");
        }else {
            libros.forEach(System.out::println);
        }
    }

    private void mostrarAutoresListados() {
        autores = repository2.findAll();
        if(autores.isEmpty()){
            System.out.println("Aun no hay autores registrados");
        }else{
            autores.stream().forEach(System.out::println);
        }
    }


    private void autoresVivosPorAnio() {
        System.out.println("De que año estamos hablando?");
        Long anio = scanner.nextLong();
        try {

            List<Autor> autoresVivos = repository2.autorVivoEnCiertoAnio(anio);
            if(autoresVivos.isEmpty()){
                System.out.println("no hay nadie vivo en este año");

            }else{
                autoresVivos.stream().forEach(System.out::println);
            }
        }catch (InputMismatchException e){
            System.out.println("Introduce un año valido");
        }


    }

    private  void librosPorIdioma() {
        System.out.println("En que idioma quieres buscar");
        String idioma = scanner.nextLine().toLowerCase();
        String lenguaje = idiomaLibro(idioma);
        libros = findByLenguajes(lenguaje);
        if (libros.isEmpty()) {
            System.out.println("No hay libros en ese idioma");
        }else{
            libros.stream().forEach(System.out::println);
        }
    }

    private String idiomaLibro(String idioma) {
        switch (idioma) {
            case "ingles":
                return "en";

            case "frances":
                return "fr";

            case "portugues":
                return "pt";

            case "español":
                return "es";

            case "ruso":
                return "ru";

            case "italiano":
                return "it";

            default:
                System.out.println("Ese idioma no lo tengo, pero para no ser mala onda te dire los que esten en español");
                return "es";
        }
    }

        public List<Libro> findByLenguajes(String lenguaje) {
            return repository1.findAll().stream()
                    .filter(libro -> libro.getLenguajes().contains(lenguaje))
                    .collect(Collectors.toList());
        }


//Metodos que pudieron ser pero ya no
//        private void buscarLibro(){
//            DatosLibro datosLibro = getDatosLibro();//creamos la plantilla de datos para maniobrar
//            if(datosLibro != null){
//
//                libroOptional = repository1.findByTitulo(datosLibro.titulo()); //traemos info de la base de datos
//                if (libroOptional.isPresent()){
//                    System.out.println("Libro existente");
//                }else {
//                    List<DatosAutor> trycatchAutor = datosLibro.autor();
//                    if(!trycatchAutor.isEmpty()) {
//                        DatosAutor datosAutor = datosLibro.autor().get(0);//creamos la plantilla de datos para maniobrar
//                        autorOptional = repository2.findByNombreContainsIgnoreCase(datosAutor.nombre());
//                        if (autorOptional.isPresent()) {
//                            Autor autor = new Autor(datosAutor);
//                            Libro libro = new Libro(datosLibro, autor);
//                            repository1.save(libro);
//
//                        } else {
//                            Autor autor = new Autor(datosAutor);
//                            Libro libro = new Libro(datosLibro, autor);
//                            repository2.save(autor);
//                            repository1.save(libro);
//
//                        }
//                    }else {
//                        Autor autorAnonimo = new Autor();
//                        autorOptional = repository2.findByNombreContainsIgnoreCase(autorAnonimo.getNombre());
//                        Libro libro = new Libro(datosLibro, autorAnonimo);
//                        repository1.save(libro);
//                    }
//                }
//
//            }else{
//                System.out.println("libro no encontrado");
//            }
//        }

//    private  void librosPorIdioma() {
//        System.out.println("En que idioma quieres buscar");
//        String idioma = scanner.nextLine().toLowerCase();
//        String lenguaje = idiomaLibro(idioma);
//        System.out.println("Lenguaje escogido:" + lenguaje);
//        List<Libro> librosLenguajes = repository1.findByLenguajes(lenguaje);
//        if (librosLenguajes.isEmpty()) {
//            System.out.println("No hay libros en ese idioma");
//        }else{
//            libros.stream().forEach(System.out::println);
//        }
//    }
//
//    private String idiomaLibro(String idioma) {
//        switch (idioma) {
//            case "ingles":
//                return "{en}";
//
//            case "frances":
//                return "{fr}";
//
//            case "portugues":
//                return "{pt}";
//
//            case "español":
//                return "{es}";
//
//            case "ruso":
//                return "{ru}";
//
//            case "italiano":
//                return "{it}";
//
//            default:
//                System.out.println("Ese idioma no lo tengo, pero para no ser mala onda te dire los que esten en español");
//                return "{es}";
//        }
//    }

}
