
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.sistemaempleados.modelo.Empleado" %>
<%@ page import="java.util.List" %>

<%
  Empleado e = (Empleado) request.getAttribute("empleado");
  List<String> titulos = (List<String>) request.getAttribute("titulos");
  List<String> departamentos = (List<String>) request.getAttribute("departamentos");
  List<Integer> salarios = (List<Integer>) request.getAttribute("salarios");
  //boolean esGerente = (boolean) request.getAttribute("esGerente");
%>

<html>
<head>
  <title>Detalle del Empleado</title>
</head>
<body>
<h2>Información del empleado</h2>

<% if (e != null) { %>
<p><strong>Número:</strong> <%= e.getEmpNo() %></p>
<p><strong>Nombre:</strong> <%= e.getFirstName() %> <%= e.getLastName() %></p>
<p><strong>Género:</strong> <%= e.getGender() %></p>
<p><strong>Fecha de contratación:</strong> <%= e.getHireDate() %></p>

<h3>Títulos</h3>
<ul>
  <% for (String t : titulos) { %>
  <li><%= t %></li>
  <% } %>
</ul>

<h3>Departamentos</h3>
<ul>
  <% for (String d : departamentos) { %>
  <li><%= d %></li>
  <% } %>
</ul>

<h3>Salarios</h3>
<ul>
  <% for (Integer s : salarios) { %>
  <li>$<%= s %></li>
  <% } %>
</ul>
<% } else { %>
<p>Empleado no encontrado.</p>
<% } %>

<br>
<a href="empleados?pagina=<%= request.getAttribute("pagina") != null ? request.getAttribute("pagina") : 1 %>">← Volver</a>
</body>
</html>