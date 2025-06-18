
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Buscar Empleado</title>
</head>
<body>
<h2>Buscar empleado</h2>

<form action="buscar-empleado" method="post">
  <label>Número:</label>
  <input type="text" name="empNo" required />
  <input type="submit" value="Buscar" />
</form>

<br>
<a href="index.jsp">Volver</a>
</body>
</html>