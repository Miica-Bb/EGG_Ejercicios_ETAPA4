package libreria.persistencia;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;

public class LibroDAO extends DAO <Libro> {
    private final Scanner leer;
    private final AutorDAO autorDao;
    private final EditorialDAO editorialDao;

    public LibroDAO() {
        leer = new Scanner(System.in).useDelimiter("\n");
        autorDao = new AutorDAO();
        editorialDao = new EditorialDAO();
    }

    @Override
    public void persistirEntidad(Libro object) {
        super.persistirEntidad(object);
    }
    
    public List<Libro> buscarLibroPorTitulo() {
        List<Libro> libro = null;
        try {
            System.out.println("Ingrese el título del libro a buscar");
            String titulo = leer.next();
            conectar();
            libro =em.createQuery("SELECT l FROM Libro l WHERE l.titulo LIKE :titulo")
                    .setParameter("titulo", titulo).getResultList();
            return libro;
        } catch (NullPointerException npe) {
            System.out.println("Libro no encontrado");
            System.out.println("Recuerde respetar las mayúsculas y minúsculas");
            return libro;
        } catch (Exception e) {
            System.out.println("No se encontró el libro");
            return libro;
        } finally {
            desconectar();
        }
    }
    
    public Libro buscarLibroPorISBN(Long isbn) {
        Libro libro = null;
        try {
            conectar();
            libro = em.find(Libro.class, isbn);
            return libro;
        } catch (NullPointerException npe) {
            System.out.println("Libro no encontrado");
            System.out.println("Compruebe si el isbn ingresado es correcto");
            return libro;
        } catch (Exception e) {
            System.out.println("No se encontró el libro");
            return libro;
        } finally {
            desconectar();
        }
    }
    
    public List<Libro> buscarLibroPorAutor() {
        List<Libro> libros = null;
        try {
            System.out.println("Ingrese el nombre completo del autor a buscar");
            String nombreAutor = leer.next();
            
            conectar();
            libros = em.createQuery("SELECT l FROM Libro l WHERE l.autor.nombre LIKE :nombreAutor")
                    .setParameter("nombreAutor", nombreAutor).getResultList();
            return libros;
        } catch (NullPointerException npe) {
            System.out.println("Libro no encontrado");
            System.out.println("Recuerde respetar las mayúsculas y minúsculas");
            return libros;
        } catch (Exception e) {
            System.out.println("No se encontró el autor");
            return libros;
        } finally {
            desconectar();
        }
    }
    
    public List<Libro> buscarLibroPorEditorial() {
        List<Libro> libros = null;
        try {
            System.out.println("Ingrese el nombre completo de la editorial a buscar");
            String nombreEditorial = leer.next();
            
            conectar();
            libros = em.createQuery("SELECT l FROM Libro l WHERE l.editorial.nombre LIKE :nombreEditorial")
                    .setParameter("nombreEditorial", nombreEditorial).getResultList();
            return libros;
        } catch (NullPointerException npe) {
            System.out.println("Libro no encontrado");
            System.out.println("Recuerde respetar las mayúsculas y minúsculas");
            return libros;
        } catch (Exception e) {
            System.out.println("No se encontró la editorial");
            return libros;
        } finally {
            desconectar();
        }
    }
    
    public void actualizarLibro (Libro libro, Autor autor, Editorial editorial) throws Exception {
        int opcion = 0;
        
        do {
            System.out.println("Ingrese el dato del libro que desea actualizar:");
            System.out.println("1. Título");
            System.out.println("2. Autor");
            System.out.println("3. Editorial");
            System.out.println("4. ISBN");
            System.out.println("5. Año");
            System.out.println("6. Cantidad de ejemplares totales");
            opcion = leer.nextInt();
            
            if (opcion < 1 || opcion > 6) {
                System.out.println("Ingreso incorrecto, intente nuevamente");
            }    
        } while (opcion < 1 || opcion > 6);
        
        try {
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese nuevo título del libro");
                    libro.setTitulo(leer.next());
                    super.actualizarEntidad(libro);
                    break;
                case 2:
                    autorDao.actualizarAutor(autor);
                    libro.setAutor(autor);
                    super.actualizarEntidad(libro);
                    break;
                case 3:
                    editorialDao.actualizarEditorial(editorial);
                    libro.setEditorial(editorial);
                    super.actualizarEntidad(libro);
                   break;
                case 4:
                    System.out.println("Ingrese el nuevo ISBN");
                    libro.setIsbn(leer.nextLong());
                    super.actualizarEntidad(libro);
                    break;
                case 5:
                    System.out.println("Ingrese el nuevo año del libro");
                    libro.setAnio(leer.nextInt());
                    super.actualizarEntidad(libro);
                    break;
                case 6:
                    System.out.println("Ingrese la cantidad de ejemplares totales");
                    libro.setEjemplares(leer.nextInt());
                    libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
                    super.actualizarEntidad(libro);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar libro");
        }
    }
    
    public void borrarLibro(Long isbn) throws Exception {
        try {
            Libro libro = buscarLibroPorISBN(isbn);
            super.borrarEntidad(libro);
        } catch (Exception e) {
            System.out.println("Error al borrar libro");
        }
    }
    
    public List<Libro> listarLibros() {
        List<Libro> libros = null;
        
        try {
            conectar();
            libros = em.createQuery("SELECT L FROM Libro l").getResultList();
            return libros;
        } catch (Exception e) {
            System.out.println("Error al listar libros");
            return libros;
        } finally {
            desconectar();
        }
    }
    
    public Boolean comprobarNuevoIngreso(List<Libro> libros, Libro libro) {
        Boolean libroExistente = false;
        Boolean condicion;
        
        for(Libro elemento : libros) {
            condicion = elemento.getTitulo().equalsIgnoreCase(libro.getTitulo()) 
                    && elemento.getAutor().getNombre().equalsIgnoreCase(libro.getAutor().getNombre()) 
                    && elemento.getEditorial().getNombre().equalsIgnoreCase(libro.getEditorial().getNombre());
            
            if (condicion) {
                libroExistente = true;
                break;
            }
        }
            
        return libroExistente;
    }
}
