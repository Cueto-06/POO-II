package com.example.sistemaempleados.dao;

import com.example.sistemaempleados.modelo.Departamento;
import com.example.sistemaempleados.modelo.Empleado;
import com.example.sistemaempleados.DBConnection;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DepartamentoDAO {

    public static List<Departamento> obtenerDepartamentos() {
        List<Departamento> lista = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM departments")) {

            while (rs.next()) {
                Departamento d = new Departamento(
                        rs.getString("dept_no"),
                        rs.getString("dept_name")
                );
                lista.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static List<Departamento> obtenerDepartamentosPaginados(int pagina, int cantidad) {
        return obtenerDepartamentos().stream()
                .skip((long)(pagina - 1) * cantidad)
                .limit(cantidad)
                .collect(Collectors.toList());
    }

    public static List<Empleado> obtenerEmpleadosPorDepartamento(String deptNo) {
        List<Empleado> lista = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT e.* FROM employees e " +
                             "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                             "WHERE de.dept_no = ?")) {

            stmt.setString(1, deptNo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Empleado e = new Empleado(
                        rs.getInt("emp_no"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("hire_date")
                );
                lista.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}