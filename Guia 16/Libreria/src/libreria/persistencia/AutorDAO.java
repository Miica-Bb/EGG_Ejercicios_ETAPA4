package libreria.persistencia;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;

public class AutorDAO extends DAO <Autor> {
    private Scanner leer;

    public AutorDAO() {
        leer = new Scanner(System.in).useDelimiter("\n");
    }
    
    @Override
    public void persistirEntidad(Autor object) {
        super.persistirEntidad(object);
    }
    
    public Autor buscarAutorPorNombre(String nombre) {
        Autor autor = null;
        try {
            conectar();
            autor = (Autor) em.createQuery("SELECT a FROM Autor a WHERE a.nombre LIKE :nombre").setParameter("nombre", nombre).getSingleResult();
            return autor;
        } catch (NullPointerException npe) {
            System.out.println("Autor no encontrado");
            System.out.println("Recuerde respetar las mayúsculas y minúsculas");
            return autor;
        } catch (Exception e) {
            System.out.println("Error al buscar autor");
            return autor;
        } finally {
            desconectar();
        }
    }
    
    public Autor buscarAutorPorID(Integer id) {
        Autor autor = null;
        try {
            conectar();
            autor = em.find(Autor.class, id);
            return autor;
        } catch (NullPointerException npe) {
            System.out.println("Autor no encontrado");
            System.out.println("Chequee la id ingresada");
            return autor;
        } catch (Exception e) {
            System.out.println("Error al buscar autor");
            return autor;
        } finally {
            desconectar();
        }
    }
    
    public void actualizarAutor(Autor autor) throws Exception{
        try {
            System.out.println("Ingrese el nuevo nombre del autor");
            autor.setNombre(leer.next());
            super.actualizarEntidad(autor);
        } catch (Exception e) {
            System.out.println("No se pudo actualizar");
        }

    }
    
    public void eliminarAutor(Integer id) throws Exception{
        try {
            Autor autor = buscarAutorPorID(id);
            super.borrarEntidad(autor);
        } catch (Exception e) {
            System.out.println("No se pudo borrar autor");
        }
    }
    
    public List<Autor> listarAutores() {
        List<Autor> autores = null;
        
        try {
            conectar();
            autores = em.createQuery("SELECT a FROM Autor a").getResultList();
            return autores;
        } catch (Exception e) {
            System.out.println("No se pudo crear lista de autores");
            return autores;
        } finally {
            desconectar();
        }
    }
    
    public Boolean comprobarNuevoIngreso(List<Autor> autores, Autor autor) {
        Boolean autorExistente = false;
        
        for(Autor elemento : autores) {
            if (elemento.getNombre().equalsIgnoreCase(autor.getNombre())) {
                autorExistente = true;
                break;
            }
        }
            
        return autorExistente;
    }
}
