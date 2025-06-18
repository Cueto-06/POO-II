package com.example.sistemaempleados;

import com.example.sistemaempleados.dao.DepartamentoDAO;
import com.example.sistemaempleados.modelo.Departamento;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/departamentos")
public class DepartamentoServlet extends HttpServlet {
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

        List<Departamento> departamentos = DepartamentoDAO.obtenerDepartamentosPaginados(pagina, 50);

        request.setAttribute("departamentos", departamentos);
        request.setAttribute("pagina", pagina);

        request.getRequestDispatcher("/departamentos.jsp").forward(request, response);
    }
}