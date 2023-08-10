package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;
import libreria.persistencia.LibroDAO;

public class LibroServicios {
    private final Scanner leer;
    private final LibroDAO libroDAO;
    private final AutorServicios autorServ;
    private final EditorialServicios editorialServ;

    public LibroServicios() {
        leer = new Scanner(System.in).useDelimiter("\n");
        libroDAO = new LibroDAO();
        autorServ = new AutorServicios();
        editorialServ = new EditorialServicios();
    }
    
    public Libro crearLibro() {
        Libro libro = new Libro();
        String nombreAutor;
        String nombreEditorial;
        
        try {
            System.out.println("Ingrese el ISBN");
            libro.setIsbn(leer.nextLong());
            System.out.println("Ingrese el título");
            libro.setTitulo(leer.next());
            System.out.println("Ingrese el año de publicación del libro");
            libro.setAnio(leer.nextInt());
            
            System.out.println("Ingrese el nombre completo del autor");
            nombreAutor = leer.next();
            Autor autor = autorServ.buscarPorNombre(nombreAutor);
            libro.setAutor(autor);
            
            System.out.println("Ingrese el nombre de la editorial");
            nombreEditorial = leer.next();
            Editorial editorial = editorialServ.buscarPorNombre(nombreEditorial);
            libro.setEditorial(editorial);
            
            System.out.println("Ingrese la cantidad total de ejemplares");
            libro.setEjemplares(leer.nextInt());
            libro.setEjemplaresRestantes(libro.getEjemplares());
            libro.setEjemplaresPrestados(0);
            
            return libro;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Libro buscarPorISBN(Long isbn) {
        try {
            return libroDAO.buscarLibroPorISBN(isbn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Libro> buscarPorTitulo() {
        try {
            return libroDAO.buscarLibroPorTitulo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Libro> buscarPorAutor() {
        try {
            return libroDAO.buscarLibroPorAutor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Libro> buscarPorEditorial() {
        try {
            return libroDAO.buscarLibroPorEditorial();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void listarLibros() {
        List<Libro> libros = libroDAO.listarLibros();
        
        for(Libro elemento : libros) {
            System.out.println(elemento);
        }
        
    }
    
    public void persistirLibro(Libro libro) {
        Boolean existente;
        List<Libro> libros = libroDAO.listarLibros();
        existente = libroDAO.comprobarNuevoIngreso(libros, libro);
            
            if (existente) {
                System.out.println("Ese libro ya se encuentra en la base de datos");
            } else {
                libroDAO.persistirEntidad(libro);
            }
    }
    
    public void actualizarLibro() {
        Libro libro;
        Autor autor;
        Editorial editorial;
        
        try {
            System.out.println("Ingrese el ISBN del libro a actualizar");
            Long isbn = leer.nextLong();
            libro = libroDAO.buscarLibroPorISBN(isbn);
            autor = libro.getAutor();
            editorial = libro.getEditorial();

            libroDAO.actualizarLibro(libro, autor, editorial);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void eliminarLibro() {
        try {
            System.out.println("Ingrese el ISBN del libro que desea eliminar");
            Long isbn = leer.nextLong();
            
            libroDAO.borrarLibro(isbn);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
