package tienda.Servicios;

import java.util.Scanner;
import tienda.Entidades.Fabricante;
import tienda.Persistencia.FabricanteDAO;

public class FabricanteServicios {

    private final Scanner leer;
    private final FabricanteDAO DAO;

    public FabricanteServicios() {
        leer = new Scanner(System.in).useDelimiter("\n");
        DAO = new FabricanteDAO();
    }
    
    public Fabricante crearFabricante() {
        Fabricante fabricante = new Fabricante();
        
        try {
            System.out.print("Ingrese nombre del fabricante: ");
            fabricante.setNombre(leer.next());
            System.out.println("Ingrese el código del fabricante");
            fabricante.setCodigo(leer.nextInt());
            
            return fabricante;
        } catch (Exception e) {
            e.getMessage();
            return fabricante;
        }
    }
    
    public void agregarFabricante(Fabricante fabricante) throws Exception {
        try{
            DAO.guardarProducto(fabricante);
        } catch (Exception e) {
            throw e;
        }

    }
    
    public Fabricante buscarPorNombre() throws Exception {
        Fabricante fabricante = null;
        try {
            System.out.println("Ingrese el nombre del fabricante");
            String nombre = leer.next();
            fabricante = DAO.listarFabricantePorNombre(nombre);
            
            return fabricante;
        } catch (Exception e) {
            e.getMessage();
            return fabricante;
        }

        
        
    }
}
