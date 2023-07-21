  -- 1) Mostrar el nombre de todos los jugadores ordenados alfabéticamente.
SELECT Nombre FROM jugadores ORDER BY Nombre ASC;

  -- 2) Mostrar el nombre de los jugadores que sean pivots (‘C’) y que pesen más de 200 libras, ordenados por nombre alfabéticamente.
SELECT Nombre FROM jugadores WHERE Posicion = 'C' AND Peso > 200 ORDER BY Nombre ASC;

  -- 3) Mostrar el nombre de todos los equipos ordenados alfabéticamente.
SELECT Nombre FROM equipos ORDER BY Nombre ASC;

  -- 4) Mostrar el nombre de los equipos del este (East).
SELECT Nombre FROM equipos WHERE Conferencia = 'East';

  -- 5) Mostrar los equipos donde su ciudad empieza con la letra ‘c’, ordenados por nombre.
SELECT * FROM equipos WHERE Ciudad LIKE 'C%' ORDER BY Nombre ASC;

  -- 6) Mostrar todos los jugadores y su equipo ordenados por nombre del equipo.
SELECT j.Nombre, j.Procedencia, j.Altura, j.Peso, j.Posicion, j.Nombre_equipo As 'Equipo', e.Ciudad, e.Conferencia, e.Division
 FROM jugadores j INNER JOIN equipos e ON j.Nombre_equipo = e.Nombre ORDER BY Nombre_equipo ASC;
 
  -- 7) Mostrar todos los jugadores del equipo “Raptors” ordenados por nombre.
SELECT j.Nombre FROM jugadores j INNER JOIN equipos e ON j.Nombre_equipo = e.Nombre WHERE e.Nombre = 'Raptors' ORDER BY j.Nombre;

  -- 8) Mostrar los puntos por partido del jugador ‘Pau Gasol’.
SELECT Puntos_por_partido, temporada FROM estadisticas e INNER JOIN jugadores j ON e.jugador = j.codigo WHERE j.Nombre = 'Pau Gasol';

  -- 9) Mostrar los puntos por partido del jugador ‘Pau Gasol’ en la temporada ’04/05′
SELECT Puntos_por_partido FROM estadisticas e INNER JOIN jugadores j ON e.jugador = j.codigo
 WHERE j.Nombre = 'Pau Gasol' AND e.temporada = '04/05';
 
  -- 10) Mostrar el número de puntos de cada jugador en toda su carrera.
SELECT j.Nombre, SUM(Puntos_por_partido) AS 'Puntos en carrera' FROM jugadores j
 LEFT JOIN estadisticas e ON j.codigo = e.jugador GROUP BY j.Nombre ORDER BY j.Nombre;
 
  -- 11) Mostrar el número de jugadores de cada equipo.
SELECT e.Nombre AS 'Nombre Equipo', COUNT(j.codigo) AS 'Cantidad Jugadores' FROM jugadores j
 INNER JOIN equipos e ON j.Nombre_equipo = e.Nombre
 GROUP BY e.Nombre ORDER BY e.Nombre;
 
  -- 12) Mostrar el jugador que más puntos ha realizado en toda su carrera.
SELECT j.Nombre, SUM(Puntos_por_partido) AS 'Puntos en carrera' FROM jugadores j
 INNER JOIN estadisticas e ON j.codigo = e.jugador GROUP BY j.Nombre ORDER BY SUM(Puntos_por_partido) DESC LIMIT 1;
 
  -- 13) Mostrar el nombre del equipo, conferencia y división del jugador más alto de la NBA. 
SELECT e.Nombre, e.Conferencia, e.Division FROM equipos e INNER JOIN jugadores j ON e.Nombre = j.Nombre_equipo
 WHERE j.Altura = (SELECT MAX(Altura) FROM jugadores);
 
  -- 14) Mostrar el partido o partidos (equipo_local, equipo_visitante y diferencia) con mayor diferencia de puntos.
SELECT equipo_local, equipo_visitante, ABS(puntos_local - puntos_visitante) AS 'Diferencia puntos' FROM partidos
 WHERE (SELECT MAX(ABS(puntos_local - puntos_visitante)) FROM partidos) = ABS(puntos_local - puntos_visitante);
 
  -- 15) Mostrar quien gana en cada partido (codigo, equipo_local, equipo_visitante, equipo_ganador), en caso de empate sera null.
SELECT codigo, equipo_local, equipo_visitante, puntos_local, puntos_visitante, IF(puntos_local > puntos_visitante, equipo_local,
 IF(puntos_local < puntos_visitante, equipo_Visitante, IF(puntos_local = puntos_visitante, null, '???'))) AS 'Ganador'
 FROM partidos;