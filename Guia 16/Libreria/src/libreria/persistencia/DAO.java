package libreria.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class DAO <T> {                               //T referencia a objeto genérico. En la subclase hay que aclarar el objeto
    private final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("LibreriaPU");
    protected EntityManager em = EMF.createEntityManager();
    
    protected void conectar() {
        if (!em.isOpen()) {
            em = EMF.createEntityManager();
        }
    }

    protected void desconectar() {
        if (em.isOpen()) {
            em.close();
        }
    }
    
    protected void persistirEntidad(T object) {
        try {
            conectar();
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar");
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();                //La idea del rollback es que si quedó abierta la transacción (antes del commit)
            }                                                  //volver atrás, descartar cambios
            
        } finally {
            desconectar();                                     //Lo pongo en finally para que desconecte independientemente de si hubo error
        }
        
    }

    protected void actualizarEntidad(T object) {
        try {
            conectar();
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            System.out.println("No se pudo actualizar");
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();                
            }  
            
        } finally {
            desconectar();
        }

    }
    
    protected void borrarEntidad(T object) {
        try {
            conectar();
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("No se pudo borrar");
            e.getMessage();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();                
            }
            
        } finally {
            desconectar();
        }
    }

}
