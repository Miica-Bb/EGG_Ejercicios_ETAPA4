  -- 1a) Lista el nombre de todos los productos que hay en la tabla producto.
SELECT nombre FROM producto;

  -- 2a) Lista los nombres y los precios de todos los productos de la tabla producto.
SELECT nombre, precio FROM producto;

  -- 3a) Lista todas las columnas de la tabla producto.
SELECT * FROM producto;

  -- 4a) Lista los nombres y los precios de todos los productos de la tabla producto, redondeando el valor del precio.
SELECT nombre, ROUND(precio) FROM producto;

  -- 5a) Lista el código de los fabricantes que tienen productos en la tabla producto.
SELECT codigo_fabricante AS 'Codigo de fabricantes con productos' FROM producto;

  -- 6a) Lista el código de los fabricantes que tienen productos en la tabla producto, sin mostrar los repetidos.
SELECT codigo_fabricante AS 'Codigo de fabricantes con productos' FROM producto GROUP BY codigo_fabricante;

  -- 7a) Lista los nombres de los fabricantes ordenados de forma ascendente.
SELECT nombre FROM fabricante ORDER BY nombre ASC;

  -- 8a) Lista los nombres de los productos ordenados en primer lugar por el nombre de forma
  --    ascendente y en segundo lugar por el precio de forma descendente.
SELECT nombre, precio FROM producto ORDER BY nombre ASC, precio DESC;

  -- 9a) Devuelve una lista con las 5 primeras filas de la tabla fabricante.
SELECT * FROM fabricante LIMIT 5;

  -- 10a) Lista el nombre y el precio del producto más barato. (Utilice solamente las cláusulas ORDER BY y LIMIT)
SELECT nombre, precio FROM producto ORDER BY precio ASC LIMIT 1;

  -- 11a) Lista el nombre y el precio del producto más caro. (Utilice solamente las cláusulas ORDER BY y LIMIT)
SELECT nombre, precio FROM producto ORDER BY precio DESC LIMIT 1;

  -- 12a) Lista el nombre de los productos que tienen un precio menor o igual a $120.
SELECT nombre FROM producto WHERE precio <= 120;

  -- 13a) Lista todos los productos que tengan un precio entre $60 y $200. Utilizando el operador BETWEEN.
SELECT * FROM producto WHERE precio BETWEEN 60 AND 200;

  -- 14a) Lista todos los productos donde el código de fabricante sea 1, 3 o 5. Utilizando el operador IN.
SELECT * FROM producto WHERE codigo_fabricante IN (1, 3, 5);

  -- 15a) Devuelve una lista con el nombre de todos los productos que contienen la cadena Portátil en el nombre.
SELECT nombre FROM producto WHERE nombre LIKE '%Portátil%';


                     ----- ------------------------- CONSULTAS MULTITABLA I -------------------------- ----
                     
  -- 1b) Devuelve una lista con el código del producto, nombre del producto, código del fabricante
  --     y nombre del fabricante, de todos los productos de la base de datos.
SELECT producto.codigo, producto.nombre, codigo_fabricante, fabricante.nombre AS 'Fabricante' FROM fabricante
 INNER JOIN producto ON fabricante.codigo = producto.codigo_fabricante;
 
  -- 2b) Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos
  --     los productos de la base de datos. Ordene el resultado por el nombre del fabricante, por
  --     orden alfabético.
SELECT producto.nombre, precio, fabricante.nombre AS 'Fabricante' FROM fabricante
 INNER JOIN producto ON fabricante.codigo = producto.codigo_fabricante
 ORDER BY fabricante.nombre ASC;
 
  -- 3b) Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más barato.
SELECT producto.nombre, precio, fabricante.nombre FROM fabricante
 INNER JOIN producto ON fabricante.codigo = producto.codigo_fabricante
 ORDER BY precio ASC LIMIT 1;
 
  -- 4b) Devuelve una lista de todos los productos del fabricante Lenovo.
SELECT producto.codigo, producto.nombre, precio, fabricante.nombre AS 'Fabricante' FROM producto
 INNER JOIN fabricante ON producto.codigo_fabricante = fabricante.codigo
 WHERE fabricante.nombre = 'Lenovo';
 
  -- 5b) Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que $200.
SELECT producto.codigo, producto.nombre, precio, fabricante.nombre AS 'Fabricante' FROM producto
 INNER JOIN fabricante ON producto.codigo_fabricante = fabricante.codigo
 WHERE fabricante.nombre = 'Crucial' AND precio > 200;
 
  -- 6b) Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard. Utilizando el operador IN.
SELECT producto.codigo, producto.nombre, precio, fabricante.nombre AS 'Fabricante' FROM producto
 INNER JOIN fabricante ON producto.codigo_fabricante = fabricante.codigo
 WHERE fabricante.nombre IN ('Asus', 'Hewlett-Packard');
 
  -- 7b) Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio 
  --     mayor o igual a $180. Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el
  --     nombre (en orden ascendente)
SELECT producto.nombre, precio, fabricante.nombre AS 'Fabricante' FROM producto
 INNER JOIN fabricante ON producto.codigo_fabricante = fabricante.codigo
 WHERE precio >= 180 ORDER BY precio DESC, producto.nombre ASC;
 
 
                      ---- -------------------------- CONSULTAS MULTITABLA II -------------------------- ----
                      
  -- 1c) Devuelve un listado de todos los fabricantes que existen en la base de datos, junto con los productos que tiene cada uno de ellos.
  --     El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados.
SELECT fabricante.nombre AS 'Fabricante', producto.codigo, producto.nombre, precio FROM fabricante
 LEFT JOIN producto ON fabricante.codigo = producto.codigo_fabricante;
 
  -- 2c) Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
SELECT fabricante.nombre AS 'Fabricante', fabricante.codigo FROM fabricante
 LEFT JOIN producto ON fabricante.codigo = producto.codigo_fabricante
 WHERE producto.codigo IS NULL;


                      ---- -------------------------- SUBCONSULTAS CON WHERE --------------------------- ---- 

  -- 1d) Devuelve todos los productos del fabricante Lenovo. (Sin utilizar INNER JOIN).
SELECT codigo, nombre, precio FROM producto 
 WHERE codigo_fabricante = (SELECT codigo FROM fabricante WHERE nombre = 'Lenovo');
                      
  -- 2d) Devuelve todos los datos de los productos que tienen el mismo precio que el producto más caro del fabricante Lenovo.
  --     (Sin utilizar INNER JOIN).
INSERT INTO producto VALUES(12, 'prueba', 559, 5);    -- para probar otro producto que valga lo mismo que la lenovo cara
SELECT * FROM producto WHERE precio = (SELECT MAX(precio) FROM producto
 WHERE codigo_fabricante = (SELECT codigo FROM fabricante WHERE nombre = 'Lenovo'));
DELETE FROM producto WHERE codigo = 12;               -- para eliminar el producto inventado

  -- 3d) Lista el nombre del producto más caro del fabricante Lenovo.
SELECT nombre FROM producto WHERE codigo_fabricante = (SELECT codigo FROM fabricante WHERE nombre = 'Lenovo')
 AND precio = (SELECT MAX(precio) FROM producto WHERE codigo_fabricante = (SELECT codigo FROM fabricante WHERE nombre = 'Lenovo'));
 
  -- 4d) Lista todos los productos del fabricante Asus que tienen un precio superior al precio medio de todos sus productos.
SELECT codigo, nombre, precio FROM producto
 WHERE codigo_fabricante = (SELECT codigo FROM fabricante WHERE nombre = 'Asus')
 AND precio > (SELECT AVG((precio)) FROM producto WHERE codigo_fabricante = (SELECT codigo FROM fabricante WHERE nombre = 'Asus'));
 
 
                       ---- -------------------------- SUBCONSULTAS CON IN / NOT IN --------------------------- ---- 
                       
  -- 1e) Devuelve los nombres de los fabricantes que tienen productos asociados. (Utilizando IN o NOT IN).
SELECT fabricante.nombre FROM fabricante INNER JOIN producto ON fabricante.codigo = producto.codigo_fabricante
 WHERE producto.codigo IS NOT NULL GROUP BY fabricante.nombre;
 
  -- 2e) Devuelve los nombres de los fabricantes que no tienen productos asociados. (Utilizando IN o NOT IN).
SELECT fabricante.nombre FROM fabricante LEFT JOIN producto ON fabricante.codigo = producto.codigo_fabricante
 WHERE producto.codigo IS NULL;
 
 
                        ---- -------------------------- SUBCONSULTAS CON HAVING --------------------------- ---- 
                        
  -- 1f) Devuelve un listado con todos los nombres de los fabricantes que tienen el mismo número de productos que el fabricante Lenovo.
SELECT fabricante.nombre FROM fabricante INNER JOIN producto ON fabricante.codigo = producto.codigo_fabricante
 GROUP BY producto.codigo_fabricante HAVING COUNT(producto.codigo_fabricante) =
 (SELECT COUNT(producto.codigo_fabricante) FROM fabricante INNER JOIN producto ON fabricante.codigo = producto.codigo_fabricante
 WHERE fabricante.nombre = 'Lenovo');
 