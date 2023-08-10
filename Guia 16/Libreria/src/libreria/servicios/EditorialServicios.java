package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Editorial;
import libreria.persistencia.EditorialDAO;

public class EditorialServicios {
    private final Scanner leer;
    private final EditorialDAO editorialDAO;

    public EditorialServicios() {
        leer = new Scanner(System.in).useDelimiter("\n");
        editorialDAO = new EditorialDAO();
    }
    
    public Editorial crearEditorial() {
        Editorial editorial = new Editorial();

        try {
            System.out.print("Ingrese el nombre de la editorial: ");
            editorial.setNombre(leer.next());
            return editorial;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Editorial buscarPorID(Integer id) {
        try {
            return editorialDAO.buscarEditorialPorID(id); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Editorial buscarPorNombre(String nombre) {
        try {
            return editorialDAO.buscarEditorialPorNombre(nombre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void listarEditoriales() {
        List<Editorial> editoriales = editorialDAO.listarEditoriales();
        
        for(Editorial elemento : editoriales) {
            System.out.println(elemento);
        }
        
    }
    
    public void persistirEditorial(Editorial editorial) {
        Boolean existente;
        List<Editorial> editoriales = editorialDAO.listarEditoriales();
        existente = editorialDAO.comprobarNuevoIngreso(editoriales, editorial);
            
            if (existente) {
                System.out.println("Esa editorial ya se encuentra en la base de datos");
            } else {
                editorialDAO.persistirEntidad(editorial);
            }
    }
    
    public void actualizarEditorial() {
        try {
            int opcion;
            Editorial editorial = new Editorial();
            
            do {
                System.out.println("Ingrese una opción para buscar a la editorial que desea actualizar:");
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
                    editorial = editorialDAO.buscarEditorialPorID(id);
                    break;
                case 2:
                    System.out.println("Ingrese el nombre");
                    String nombre = leer.next();
                    editorial = editorialDAO.buscarEditorialPorNombre(nombre);
                    break;
            }
            
            editorialDAO.actualizarEditorial(editorial);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void eliminarEditorial() {
        try {
            System.out.println("Ingrese la id de la editorial que desea eliminar");
            Integer id = leer.nextInt();
            
            editorialDAO.borrarEditorial(id);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
