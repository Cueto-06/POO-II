
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.sistemaempleados.modelo.Empleado" %>
<%@ page import="java.util.List" %>
<%
  String codigoDepto = (String) request.getAttribute("codigoDepto");
  List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleadosDepto");
  int pagina = (int) request.getAttribute("pagina");
  int total = (int) request.getAttribute("total");
%>

<html>
<head>
  <title>Detalle del Departamento</title>
</head>
<body>
<h2>Empleados del Departamento <%= codigoDepto %> (página <%= pagina %>)</h2>

<% if (empleados != null && !empleados.isEmpty()) { %>
<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>ID</th>
    <th>Nombre</th>
    <th>Género</th>
    <th>Fecha de contratación</th>
  </tr>
  <% for (Empleado e : empleados) { %>
  <tr>
    <td><%= e.getEmpNo() %></td>
    <td><%= e.getFirstName() %> <%= e.getLastName() %></td>
    <td><%= e.getGender() %></td>
    <td><%= e.getHireDate() %></td>
  </tr>
  <% } %>
</table>
<% } else { %>
<p>Departamento no tiene empleados.</p>
<% } %>

<br>
<%
  int totalPaginas = (int) Math.ceil(total / 50.0);
  if (pagina < totalPaginas) {
%>
<a href="detalle-departamento?deptNo=<%= codigoDepto %>&pagina=<%= pagina + 1 %>">Siguiente página →</a>
<% } %>

<% if (pagina > 1) { %>
| <a href="detalle-departamento?deptNo=<%= codigoDepto %>&pagina=<%= pagina - 1 %>">← Página anterior</a>
<% } %>

<br><br>
<a href="departamentos">⬅ Volver</a>
</body>
</html>