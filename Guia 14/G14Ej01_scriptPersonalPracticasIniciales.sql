  -- 1) Obtener los datos completos de los empleados.
SELECT * FROM empleados;

  -- 2) Obtener los datos completos de los departamentos.            
SELECT * FROM departamentos;

  -- 3) Listar el nombre de los departamentos.
SELECT nombre_depto FROM departamentos;

  -- 4) Obtener el nombre y salario de todos los empleados
SELECT nombre AS 'Nombre Empleado', sal_emp AS 'Salario' FROM empleados;

  -- 5) Listar todas las comisiones.
SELECT comision_emp AS comisión FROM empleados;

  -- 6) Obtener los datos de los empleados cuyo cargo sea 'Secretaria'.
SELECT * FROM empleados WHERE cargo_emp = 'Secretaria';

  -- 7) Obtener los datos de los empleados vendedores, ordenados por nombre alfabéticamente.
SELECT * FROM empleados WHERE cargo_emp = 'Vendedor' ORDER BY nombre ASC;

  -- 8) Obtener el nombre y cargo de todos los empleados, ordenados por salario de menor a mayor.
SELECT nombre, cargo_emp AS cargo FROM empleados ORDER BY sal_emp ASC;

  -- 9) Obtener el nombre de o de los jefes que tengan su departamento situado en la ciudad de “Ciudad Real”.
SELECT nombre AS 'Nombre de Jefe' FROM empleados INNER JOIN departamentos ON empleados.id_depto = departamentos.id_depto
 WHERE ciudad = 'CIUDAD REAL' AND cargo_emp LIKE 'Jefe%';
 
  -- 10) Elabore un listado donde para cada fila, figure el alias ‘Nombre’ y ‘Cargo’ para las respectivas tablas de empleados.
SELECT nombre AS 'Nombre', cargo_emp AS 'Cargo' FROM empleados;

  -- 11) Listar los salarios y comisiones de los empleados del departamento 2000, ordenado por comisión de menor a mayor.
SELECT sal_emp AS 'Salario', comision_emp AS 'Comisión' FROM empleados
 RIGHT JOIN departamentos ON empleados.id_depto = departamentos.id_depto
 WHERE empleados.id_depto = 2000 ORDER BY comision_emp ASC;
 
  -- 12) Obtener el valor total a pagar a cada empleado del departamento 3000, que resulta de: sumar el salario
      -- y la comisión, más una bonificación de 500. Mostrar el nombre del empleado y el total a pagar, en orden alfabético.
SELECT nombre, (sal_emp + comision_emp + 500) AS 'Total a Pagar' FROM empleados ORDER BY nombre ASC;

  -- 13) Muestra los empleados cuyo nombre empiece con la letra J.
SELECT * FROM empleados WHERE nombre LIKE 'J%';

  -- 14) Listar el salario, la comisión, el salario total (salario + comisión) y nombre,
	 -- de aquellos empleados que tienen comisión superior a 1000.
SELECT nombre AS 'Nombre', sal_emp AS 'Salario', comision_emp AS 'Comisión', (sal_emp + comision_emp) AS 'Salario Total'
 FROM empleados WHERE comision_emp > 1000;

  -- 15) Obtener un listado similar al anterior, pero de aquellos empleados que NO tienen comisión.
SELECT nombre AS 'Nombre', sal_emp AS 'Salario', comision_emp AS 'Comisión', (sal_emp + comision_emp) AS 'Salario Total'
 FROM empleados WHERE comision_emp = 0;
 
  -- 16) Obtener la lista de los empleados que ganan una comisión superior a su sueldo.
SELECT * FROM empleados WHERE comision_emp > sal_emp;

  -- 17) Listar los empleados cuya comisión es menor o igual que el 30% de su sueldo.
SELECT * FROM empleados WHERE comision_emp <= (sal_emp * 0.3);

  -- 18) Hallar los empleados cuyo nombre no contiene la cadena “MA”.
SELECT * FROM empleados WHERE nombre NOT LIKE '%MA%';

  -- 19) Obtener los nombres de los departamentos que sean “Ventas”, “Investigación” o "Mantenimiento".
SELECT nombre_depto AS 'Nombre de Departamento' FROM departamentos
 WHERE nombre_depto = 'VENTAS' OR nombre_depto = 'INVESTIGACIÓN' OR nombre_depto = 'MANTENIMIENTO';
 
  -- 20) Ahora obtener el contrario, los nombres de los departamentos que no sean “Ventas” ni “Investigación” ni "Mantenimiento".
SELECT nombre_depto AS 'Nombre Departamento' FROM departamentos
 WHERE nombre_depto != 'VENTAS' AND nombre_depto != 'INVESTIGACIÓN' AND nombre_depto != 'MANTENIMIENTO';
 
  -- 21) Mostrar el salario más alto de la empresa.
SELECT nombre, sal_emp AS 'Salario' FROM empleados ORDER BY sal_emp DESC LIMIT 1; -- modo 1
SELECT MAX(sal_emp) AS 'Salario más alto' FROM empleados; -- modo 2

  -- 22) Mostrar el nombre del último empleado de la lista por orden alfabético.
SELECT MAX(nombre) AS 'Último empleado' FROM empleados ORDER BY nombre ASC;

  -- 23) Hallar el salario más alto, el más bajo y la diferencia entre ellos.
SELECT MAX(sal_emp) AS 'Mayor salario', MIN(sal_emp) AS 'Menor salario', (MAX(sal_emp) - MIN(sal_emp)) AS 'Diferencia' FROM empleados;

  -- 24) Hallar el salario promedio por departamento.
SELECT nombre_depto, AVG(sal_emp) AS 'Promedio salarios' FROM empleados
 INNER JOIN departamentos ON empleados.id_depto = departamentos.id_depto
 GROUP BY nombre_depto;
 
  -- 25) Hallar los departamentos que tienen más de tres empleados. Mostrar el número de empleados de esos departamentos.
SELECT nombre_depto AS 'Nombre Departamento', COUNT(nombre_depto) AS 'Cantidad Empleados' FROM empleados
 INNER JOIN departamentos ON empleados.id_depto = departamentos.id_depto
 GROUP BY nombre_depto HAVING COUNT(nombre_depto) > 3;
 
  -- 26) Hallar los departamentos que no tienen empleados.
SELECT nombre_depto AS 'Nombre Departamento', COUNT(nombre_depto) AS 'Cantidad Empleados'  FROM empleados
 INNER JOIN departamentos ON empleados.id_depto = departamentos.id_depto
 GROUP BY nombre_depto HAVING COUNT(nombre_depto) = 0;
 
  -- 28) Mostrar la lista de los empleados cuyo salario es mayor o igual que el promedio de la empresa. Ordenarlo por departamento.
SELECT * FROM empleados 
 INNER JOIN departamentos ON empleados.id_depto = departamentos.id_depto
 WHERE sal_emp >= (SELECT AVG(sal_emp) FROM empleados) ORDER BY nombre_depto;