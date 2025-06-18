package com.example.sistemaempleados;

import com.example.sistemaempleados.dao.EmpleadoDAO;
import com.example.sistemaempleados.modelo.Empleado;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/empleados")
public class EmpleadoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pagina = 1;
        try {
            String paginaStr = request.getParameter("pagina");
            if (paginaStr != null) {
                pagina = Integer.parseInt(paginaStr);
            }
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        // Lista de empleados para la página actual
        List<Empleado> empleados = EmpleadoDAO.obtenerEmpleadosPaginados(pagina, 50);

        // Enviar datos al JSP
        request.setAttribute("empleados", empleados);
        request.setAttribute("pagina", pagina);

        // Reenviar al jsp
        request.getRequestDispatcher("/empleados.jsp").forward(request, response);
    }
}
