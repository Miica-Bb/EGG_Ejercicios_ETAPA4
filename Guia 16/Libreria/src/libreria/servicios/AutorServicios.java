package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.persistencia.AutorDAO;

public class AutorServicios {
    private final Scanner leer;
    private final AutorDAO autorDAO;

    public AutorServicios() {
        leer = new Scanner(System.in).useDelimiter("\n");
        autorDAO = new AutorDAO();
    }
    
    public Autor crearAutor() {
        Autor autor = new Autor();

        try {
            System.out.print("Ingrese el nombre del Autor: ");
            autor.setNombre(leer.next());
            return autor;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    public Autor buscarPorID(Integer id) {
        try {
            return autorDAO.buscarAutorPorID(id); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Autor buscarPorNombre(String nombre) {
        try {
            return autorDAO.buscarAutorPorNombre(nombre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void listarAutores() {
        List<Autor> autores = autorDAO.listarAutores();
        
        for(Autor elemento : autores) {
            System.out.println(elemento);
        }
        
    }
    
    public void persistirAutor(Autor autor) {
        Boolean existente;
        List<Autor> autores = autorDAO.listarAutores();
        existente = autorDAO.comprobarNuevoIngreso(autores, autor);
            
            if (existente) {
                System.out.println("Ese autor ya se encuentra en la base de datos");
            } else {
                autorDAO.persistirEntidad(autor);
            }
    }
    
    public void actualizarAutor() {
        try {
            int opcion;
            Autor autor = new Autor();
            
            do {
                System.out.println("Ingrese una opción para buscar al autor que desea actualizar:");
                System.out.println("1. ID");
                System.out.println("2. Nombre completo");
                opcion = leer.nextInt();
                
                if (opcion < 1 || opcion > 2) {
                    System.out.println("Ingreso incorrecto, intente nuevamente");
                }
            } while (opcion < 1 || opcion > 2);
            
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese la ID");
                    Integer id = leer.nextInt();
                    autor = autorDAO.buscarAutorPorID(id);
                    break;
                case 2:
                    System.out.println("Ingrese el nombre");
                    String nombre = leer.next();
                    autor = autorDAO.buscarAutorPorNombre(nombre);
                    break;
            }
            
            autorDAO.actualizarAutor(autor);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void eliminarAutor() {
        try {
            System.out.println("Ingrese la id del autor que desea eliminar");
            Integer id = leer.nextInt();
            
            autorDAO.eliminarAutor(id);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
