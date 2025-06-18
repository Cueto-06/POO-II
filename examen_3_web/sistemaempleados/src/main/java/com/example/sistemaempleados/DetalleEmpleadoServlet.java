package com.example.sistemaempleados;

import com.example.sistemaempleados.dao.EmpleadoDAO;
import com.example.sistemaempleados.modelo.Empleado;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/detalle-empleado")
public class DetalleEmpleadoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int empNo = Integer.parseInt(request.getParameter("empNo"));

            // Obtener empleado principal
            Empleado empleado = EmpleadoDAO.obtenerEmpleadoConDetalles(empNo);

            // Obtener datos adicionales
            List<String> titulos = EmpleadoDAO.obtenerTitulosPorEmpleado(empNo);
            List<String> departamentos = EmpleadoDAO.obtenerDepartamentosPorEmpleado(empNo);
            List<Integer> salarios = EmpleadoDAO.obtenerSalariosPorEmpleado(empNo);
            boolean esGerente = EmpleadoDAO.esGerente(empNo);

            // Enviar a la vista
            request.setAttribute("empleado", empleado);
            request.setAttribute("titulos", titulos);
            request.setAttribute("departamentos", departamentos);
            request.setAttribute("salarios", salarios);
            request.setAttribute("esGerente", esGerente);

            request.getRequestDispatcher("/detalle_empleado.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al mostrar empleado");
        }
    }
}
