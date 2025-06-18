package com.example.sistemaempleados;

import com.example.sistemaempleados.dao.DepartamentoDAO;
import com.example.sistemaempleados.modelo.Empleado;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/detalle-departamento")
public class DetalleDepartamentoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String deptNo = request.getParameter("deptNo");
        int pagina = 1;
        try {
            String paginaStr = request.getParameter("pagina");
            if (paginaStr != null) {
                pagina = Integer.parseInt(paginaStr);
            }
        } catch (NumberFormatException ignored) {}

        if (deptNo != null) {
            List<Empleado> todos = DepartamentoDAO.obtenerEmpleadosPorDepartamento(deptNo);

            // Paginado usando streams
            List<Empleado> paginados = todos.stream()
                    .skip((long)(pagina - 1) * 50)
                    .limit(50)
                    .collect(Collectors.toList());

            request.setAttribute("empleadosDepto", paginados);
            request.setAttribute("codigoDepto", deptNo);
            request.setAttribute("pagina", pagina);
            request.setAttribute("total", todos.size());

            request.getRequestDispatcher("/detalle_departamento.jsp").forward(request, response);
        } else {
            response.sendRedirect("departamentos.jsp");
        }
    }
}