package tienda.Persistencia;

import tienda.Entidades.Fabricante;

public class FabricanteDAO extends DAO{
    public void guardarProducto (Fabricante fabricante) throws Exception {
        try {
            if (fabricante == null) {
                throw new Exception("Se debe indicar un fabricante");
            }
            
            String insert = "INSERT INTO fabricante (codigo, nombre) "
                    + "VALUES (" + fabricante.getCodigo() + ", '" + fabricante.getNombre() + "');";
            
            insertarModificarEliminar(insert);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Fabricante listarFabricantePorNombre(String nombre) throws Exception {
        Fabricante fabricante = null;
        
        try {
            String consulta = "SELECT * FROM producto WHERE nombre LIKE '?%';";
            sentenciaPreparada = conexion.prepareStatement(consulta);
            sentenciaPreparada.setString(1, nombre);
            
            resultado = sentenciaPreparada.executeQuery();
            
            while(resultado.next()) {
                fabricante = new Fabricante();
                fabricante.setCodigo(resultado.getInt("codigo"));
                fabricante.setNombre(resultado.getString("nombre"));
            }
            
            return fabricante;
        } catch (Exception e) {
            e.getMessage();
            return fabricante;
        } finally {
            desconectarBase();
        }
    }
}
