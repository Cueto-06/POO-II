package com.example.sistemaempleados;

import com.example.sistemaempleados.dao.EmpleadoDAO;
import com.example.sistemaempleados.modelo.Empleado;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/buscar-empleado")
public class BuscarEmpleadoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int empNo = Integer.parseInt(request.getParameter("empNo"));

        // Buscar directamente por ID en la base de datos
        Empleado empleado = EmpleadoDAO.obtenerEmpleadoPorId(empNo);

        if (empleado != null) {
            request.setAttribute("empleado", empleado);
            request.setAttribute("titulos", EmpleadoDAO.obtenerTitulosPorEmpleado(empNo));
            request.setAttribute("salarios", EmpleadoDAO.obtenerSalariosPorEmpleado(empNo));
            request.setAttribute("departamentos", EmpleadoDAO.obtenerDepartamentosPorEmpleado(empNo));
            request.setAttribute("esGerente", EmpleadoDAO.esGerente(empNo));
        } else {
            request.setAttribute("mensaje", "Empleado no encontrado");
        }

        request.getRequestDispatcher("resultado_busqueda.jsp").forward(request, response);
    }
}
