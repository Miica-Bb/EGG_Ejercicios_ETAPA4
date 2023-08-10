package tienda.Persistencia;

import java.util.ArrayList;
import java.util.List;
import tienda.Entidades.Producto;

public class ProductoDAO extends DAO {

    public void guardarProducto (Producto producto) throws Exception {
        try {
            if (producto == null) {
                throw new Exception("Se debe indicar un producto");
            }
            
            String insert = "INSERT INTO producto (nombre, precio, codigo_fabricante) "
                    + "VALUES ('" + producto.getNombre() + "', " + producto.getPrecio() + ", " + producto.getCodigoFabricante() + ");";
            
            insertarModificarEliminar(insert);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modificarProducto(Producto producto) throws Exception {
        try {
            if (producto == null) {
                throw new Exception("Se debe indicar un producto");
            }
            
            String update = "UPDATE producto SET "
                    + " nombre = '" + producto.getNombre() + "' , precio = " + producto.getPrecio() + " , codigo_fabricante = " + producto.getCodigoFabricante()
                    + " WHERE codigo = " + producto.getCodigo() + ";";
            
            insertarModificarEliminar(update);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void eliminarProducto(int codigo) throws Exception {
        try {
            String delete = "DELETE FROM producto WHERE codigo = " + codigo + ";";
            insertarModificarEliminar(delete);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<Producto> listarProductos() throws Exception {
        Producto producto = null;
        List<Producto> productos = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM producto;";
            consultarBase(consulta);
            
            while(resultado.next()) {
                producto = new Producto();
                producto.setCodigo(resultado.getInt("codigo"));
                producto.setCodigoFabricante(resultado.getInt("codigo_fabricante"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                productos.add(producto);
            }
            
            return productos;
        } catch (Exception e) {
            e.getMessage();
            return productos;
        } finally {
            desconectarBase();
        }
    }
    
    public List<Producto> listarNombreDeProductos() throws Exception {
        Producto producto = null;
        List<Producto> productos = new ArrayList<>();
        
        try {
            String consulta = "SELECT nombre FROM producto;";
            consultarBase(consulta);
            
            while (resultado.next()) {
                producto = new Producto();
                producto.setNombre(resultado.getString("nombre"));
                productos.add(producto);
            }
            
            return productos;
        } catch (Exception e) {
            e.getMessage();
            return productos;
        } finally {
            desconectarBase();
        }
    }
    
    public List<Producto> listarNombreYPrecioDeProductos() throws Exception {
        Producto producto = null;
        List<Producto> productos = new ArrayList<>();
        
        try {
            String consulta = "SELECT nombre, precio FROM producto;";
            consultarBase(consulta);
            
            while (resultado.next()) {
                producto = new Producto();
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                productos.add(producto);
            }
            
            return productos;
        } catch (Exception e) {
            e.getMessage();
            return productos;
        } finally {
            desconectarBase();
        }
    }
    
    public List<Producto> listarProductosPorRangoDePrecios(double min, double max) throws Exception {
        Producto producto = null;
        List<Producto> productos = new ArrayList<>();
        try {
//        System.out.println("Ingrese el valor mínimo del rango");
//        double min = leer.nextDouble();
//        System.out.println("Ingrese el valor máximo del rango");
//        double max = leer.nextDouble();
        
            String consulta = "SELECT * FROM producto WHERE precio BETWEEN ? AND ?;";
            sentenciaPreparada = conexion.prepareStatement(consulta);
            sentenciaPreparada.setDouble(1, min);
            sentenciaPreparada.setDouble(2, max);
            
            resultado = sentenciaPreparada.executeQuery();
            
            while(resultado.next()) {
                producto = new Producto();
                producto.setCodigo(resultado.getInt("codigo"));
                producto.setCodigoFabricante(resultado.getInt("codigo_fabricante"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                productos.add(producto);
            }
            
            return productos;
        } catch (Exception e) {
            e.getMessage();
            return productos;
        } finally {
            desconectarBase();
        }
    }
    
    public List<Producto> listarProductosPorNombre(String clave) throws Exception {
        Producto producto = null;
        List<Producto> productos = new ArrayList<>();
        
        try {
//            System.out.println("Ingrese la palabra clave que desea encontrar en el nombre");
//            String clave = leer.next();
            
            String consulta = "SELECT * FROM producto WHERE nombre LIKE '%?%';";
            sentenciaPreparada = conexion.prepareStatement(consulta);
            sentenciaPreparada.setString(1, clave);
            
            resultado = sentenciaPreparada.executeQuery();
            
            while(resultado.next()) {
                producto = new Producto();
                producto.setCodigo(resultado.getInt("codigo"));
                producto.setCodigoFabricante(resultado.getInt("codigo_fabricante"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                productos.add(producto);
            }
            
            return productos;
        } catch (Exception e) {
            e.getMessage();
            return productos;
        } finally {
            desconectarBase();
        }
    }
    
    public List<Producto> listarProductoMasBarato() throws Exception {
        Producto producto = null;
        List<Producto> productos = new ArrayList<>();
        
        try {
            String consulta = "SELECT nombre, precio FROM producto WHERE precio = (SELECT MIN(precio) FROM producto);";
            consultarBase(consulta);
            
            while (resultado.next()) {
                producto = new Producto();
                producto.setNombre(resultado.getString("nombre"));
                producto.setPrecio(resultado.getDouble("precio"));
                productos.add(producto);
            }
            
            return productos;
        } catch (Exception e) {
            e.getMessage();
            return productos;
        } finally {
            desconectarBase();
        }
    }
}
