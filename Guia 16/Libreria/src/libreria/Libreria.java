package libreria;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;
import libreria.servicios.AutorServicios;
import libreria.servicios.EditorialServicios;
import libreria.servicios.LibroServicios;

public class Libreria {

    public static void main(String[] args) {
        menu();
    }
    
    public static void menu() {
        AutorServicios autorServ = new AutorServicios();
        EditorialServicios editorialServ = new EditorialServicios();
        LibroServicios libroServ = new LibroServicios();
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        int opcion = 0;
        
        Autor autor = null;
        Editorial editorial = null;
        Libro libro = null;
        List<Libro> libros = null;
        
        System.out.println("");
        System.out.println("Programa para cargar libros en librería");
        System.out.println("TENER EN CUENTA...");
        System.out.println("Antes de generar un nuevo libro, deberá haberse creado previamente el autor y la editorial");
        System.out.println("De lo contrario, no podrá cargarse el libro.");
        System.out.println("__________________________________________\n");
        do {
            System.out.println("Seleccione una opción");
            System.out.println("1. Cargar autor");
            System.out.println("2. Cargar editorial");
            System.out.println("3. Cargar libro");
            System.out.println("4. Buscar autor");
            System.out.println("5. Buscar editorial");
            System.out.println("6. Buscar libro");
            System.out.println("7. actualizar autor");
            System.out.println("8. actualizar editorial");
            System.out.println("9. actualizar libro");
            System.out.println("10. Eliminar autor");
            System.out.println("11. Eliminar editorial");
            System.out.println("12. Eliminar libro");
            System.out.println("13. Listar todos los autores");
            System.out.println("14. Listar todas las editoriales");
            System.out.println("15. Listar todos los libros");
            System.out.println("16. Salir");
            opcion = leer.nextInt();
            System.out.println("");
            
            switch (opcion) {
                case 1:
                    autor = autorServ.crearAutor();
                    autorServ.persistirAutor(autor);
                    break;
                case 2:
                    editorial = editorialServ.crearEditorial();
                    editorialServ.persistirEditorial(editorial);
                    break;
                case 3:
                    libro = libroServ.crearLibro();
                    libroServ.persistirLibro(libro);
                    break;
                case 4:
                    int modo;
                    do {
                        System.out.println("Desea buscar por: ");
                        System.out.println("1. ID");
                        System.out.println("2. Nombre completo");
                        modo = leer.nextInt();
                        
                        if (modo == 1) {
                            System.out.println("Ingrese la ID");
                            Integer id = leer.nextInt();
                            autor = autorServ.buscarPorID(id);
                        } else if (modo == 2) {
                            System.out.println("Ingrese el nombre completo");
                            String nombre = leer.next();
                            autor = autorServ.buscarPorNombre(nombre);
                        }
                        
                        if (modo < 1 || modo > 2) {
                            System.out.println("Ingreso incorrecto, intente nuevamente");
                        }
                    } while (modo < 1 || modo > 2);
                    
                    System.out.println(autor.toString());
                    break;                  
                case 5:
                    int aux;
                    do {
                        System.out.println("Desea buscar por: ");
                        System.out.println("1. ID");
                        System.out.println("2. Nombre");
                        aux = leer.nextInt();
                        
                        if (aux == 1) {
                            System.out.println("Ingrese la ID");
                            Integer id = leer.nextInt();
                            editorial = editorialServ.buscarPorID(id);
                        } else if (aux == 2) {
                            System.out.println("Ingrese el nombre");
                            String nombre = leer.next();
                            editorial = editorialServ.buscarPorNombre(nombre);
                        }
                        
                        if (aux < 1 || aux > 2) {
                            System.out.println("Ingreso incorrecto, intente nuevamente");
                        }
                    } while (aux < 1 || aux > 2);
                    
                    System.out.println(editorial.toString());
                    break;                  
                case 6:
                    int opc;
                    do {
                        System.out.println("Desea buscar por: ");
                        System.out.println("1. ISBN");
                        System.out.println("2. Título");
                        System.out.println("3. Nombre del autor");
                        System.out.println("4. Nombre de la editorial");
                        opc = leer.nextInt();
                        
                        if (opc == 1) {
                            System.out.println("Ingrese el ISBN");
                            Long isbn = leer.nextLong();
                            libro = libroServ.buscarPorISBN(isbn);
                        } else if (opc == 2) {
                            libros = libroServ.buscarPorTitulo();
                        } else if (opc == 3) {
                            libros = libroServ.buscarPorAutor();
                        } else if (opc == 4) {
                            libros = libroServ.buscarPorEditorial();
                        }
                        
                        if (opc < 1 || opc > 4) {
                            System.out.println("Ingreso incorrecto, intente nuevamente");
                        }
                    } while (opc < 1 || opc > 4);
                    
                    System.out.println(libro.toString());
                    break;           
                case 7:
                    autorServ.actualizarAutor();
                    break;
                case 8:
                    editorialServ.actualizarEditorial();
                    break;
                case 9:
                    libroServ.actualizarLibro();
                    break;
                case 10:
                    autorServ.eliminarAutor();
                    break;
                case 11:
                    editorialServ.eliminarEditorial();
                    break;
                case 12:
                    libroServ.eliminarLibro();
                    break;
                case 13:
                    autorServ.listarAutores();
                    break;
                case 14:
                    editorialServ.listarEditoriales();
                    break;
                case 15:
                    libroServ.listarLibros();
                default:
                    System.out.println("Programa finalizado");
            } 
            
            if (opcion < 1 || opcion > 16) {
                System.out.println("Ingreso incorrecto, intente nuevamente");
            }
            
            System.out.println("__________________________________________\n");
            
        } while (opcion != 16);
        
    }

}
