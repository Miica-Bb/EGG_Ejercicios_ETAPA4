package tienda.Servicios;

import java.util.Scanner;
import tienda.Entidades.Fabricante;
import tienda.Entidades.Producto;
import tienda.Persistencia.ProductoDAO;

public class ProductoServicios {
    private final Scanner leer;
    private final FabricanteServicios favServ;
    private final ProductoDAO DAO;

    public ProductoServicios() {
        leer = new Scanner(System.in).useDelimiter("\n");
        favServ = new FabricanteServicios();
        DAO = new ProductoDAO();
    }
        
    public Producto crearProducto() throws Exception {
        Producto producto = new Producto();
        Fabricante fabricante;
        try {
            System.out.print("Ingrese código del producto: ");
            producto.setCodigo(leer.nextInt());
            System.out.print("Ingrese nombre del producto: ");
            producto.setNombre(leer.next());
            System.out.print("Ingrese precio del producto: ");
            producto.setPrecio(leer.nextDouble());
            System.out.print("Ingrese nombre del fabricante: ");
            String nombreFabricante = leer.next();
            fabricante = favServ.buscarPorNombre();
            producto.setCodigoFabricante(fabricante.getCodigo());
            
            return producto;
        } catch (Exception e) {
            e.getMessage();
            return producto;
        }
    }
    
    public void agregarProducto(Producto producto) throws Exception {
        try {
            DAO.guardarProducto(producto);
        } catch (Exception e) {
            throw e;
        }
    }
    
    
}
