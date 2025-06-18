
<%@ page import="java.util.*, com.example.sistemaempleados.modelo.Empleado" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Lista de Empleados</title>
</head>
<body>
<h2>Empleados (página ${pagina})</h2>

<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>No.</th>
    <th>Nombre</th>
    <th>Apellido</th>
    <th>Género</th>
    <th>Fecha de contratación</th>
  </tr>
  <%
    List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
    if (empleados != null && !empleados.isEmpty()) {
      for (Empleado e : empleados) {
  %>
  <tr>
    <td>
      <a href="detalle-empleado?empNo=<%= e.getEmpNo() %>">
        <%= e.getEmpNo() %>
      </a>
    </td>
    <td><%= e.getFirstName() %></td>
    <td><%= e.getLastName() %></td>
    <td><%= e.getGender() %></td>
    <td><%= e.getHireDate() %></td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="5">No se encontraron empleados.</td>
  </tr>
  <% } %>
</table>

<br>
<a href="empleados?pagina=<%= request.getAttribute("pagina") != null ? ((Integer)request.getAttribute("pagina")) + 1 : 2 %>">Siguiente página</a>
<% if (request.getAttribute("pagina") != null && ((Integer)request.getAttribute("pagina")) > 1) { %>
<a href="empleados?pagina=<%= ((Integer)request.getAttribute("pagina")) - 1 %>">← Página anterior</a>
<% } %>
<br><br>
<a href="index.jsp">
  <button>⬅ Volver</button>
</a>
</body>
</html>