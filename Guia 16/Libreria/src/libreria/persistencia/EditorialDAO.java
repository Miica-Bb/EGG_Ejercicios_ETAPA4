package libreria.persistencia;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Editorial;

public class EditorialDAO extends DAO <Editorial> {
    private Scanner leer;

    public EditorialDAO() {
        leer = new Scanner(System.in).useDelimiter("\n");
    }

    @Override
    public void persistirEntidad(Editorial object) {
        super.persistirEntidad(object);
    }
    
    public Editorial buscarEditorialPorNombre(String nombre) {
        Editorial editorial = null;
        
        try {
            conectar();
            editorial = (Editorial) em.createQuery("SELECT e FROM Editorial e WHERE e.nombre LIKE :nombre").setParameter("nombre", nombre).getSingleResult();
            return editorial;
        } catch (NullPointerException npe) {
            System.out.println("Editorial no encontrada");
            System.out.println("Recuerde respetar las mayúsculas y minúsculas");
            return editorial;
        } catch (Exception e) {
            System.out.println("Error al buscar la editorial");
            return editorial;
        } finally {
            desconectar();
        }
    }
    
    public Editorial buscarEditorialPorID(Integer id) {
        Editorial editorial = null;
        
        try {
            conectar();
            editorial = em.find(Editorial.class, id);
            return editorial;
        } catch (NullPointerException npe) {
            System.out.println("Editorial no encontrada");
            System.out.println("Chequee la id ingresada");
            return editorial;
        } catch (Exception e) {
            System.out.println("Error al buscar la editorial");
            return editorial;
        } finally {
            desconectar();
        }
    }
    
    public void actualizarEditorial(Editorial editorial) throws Exception {
        try {
            System.out.println("Ingrese el nuevo nombre");
            editorial.setNombre(leer.next());
            super.actualizarEntidad(editorial);
        } catch (Exception e) {
            System.out.println("Error al actualizar la editorial");
        }
    }
    
    public void borrarEditorial(Integer id) throws Exception {
        try {

            Editorial editorial = buscarEditorialPorID(id);
            super.borrarEntidad(editorial);
        } catch (Exception e) {
            System.out.println("Error al eliminar la editorial");
        }
    }
    
    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = null;
        
        try {
            conectar();
            editoriales = em.createQuery("SELECT e FROM Editorial e").getResultList();
            return editoriales;
        } catch (Exception e) {
            System.out.println("Error al crear lista");
            return editoriales;
        } finally {
            desconectar();
        }
    }
    
    public Boolean comprobarNuevoIngreso(List<Editorial> editoriales, Editorial editorial) {
        Boolean editorialExistente = false;
        
        for(Editorial elemento : editoriales) {
            if (elemento.getNombre().equalsIgnoreCase(editorial.getNombre())) {
                editorialExistente = true;
                break;
            }
        }
            
        return editorialExistente;
    }
}
