package com.example.sistemaempleados.dao;

import com.example.sistemaempleados.modelo.Empleado;
import com.example.sistemaempleados.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmpleadoDAO {

    // -----------------------------------------
    // obtener empleados paginados con Streams
    // -----------------------------------------
    public static List<Empleado> obtenerEmpleadosPaginados(int pagina, int cantidad) {
        List<Empleado> todos = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees ORDER BY emp_no")) {

            while (rs.next()) {
                Empleado e = new Empleado(
                        rs.getInt("emp_no"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("hire_date")
                );
                todos.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return todos.stream()
                .skip((long) (pagina - 1) * cantidad)
                .limit(cantidad)
                .collect(Collectors.toList());
    }

    // -----------------------------------------
    // obtener títulos de un empleado usando Streams
    // -----------------------------------------
    public static List<String> obtenerTitulosPorEmpleado(int empNo) {
        List<String[]> todos = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT emp_no, title FROM titles")) {

            while (rs.next()) {
                todos.add(new String[]{ rs.getString("emp_no"), rs.getString("title") });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return todos.stream()
                .filter(t -> Integer.parseInt(t[0]) == empNo)
                .map(t -> t[1])
                .collect(Collectors.toList());
    }

    // -----------------------------------------
    // obtener departamentos de un empleado usando Streams
    // -----------------------------------------
    public static List<String> obtenerDepartamentosPorEmpleado(int empNo) {
        List<String[]> todos = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT de.emp_no, d.dept_name FROM dept_emp de JOIN departments d ON de.dept_no = d.dept_no")) {

            while (rs.next()) {
                todos.add(new String[]{ rs.getString("emp_no"), rs.getString("dept_name") });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return todos.stream()
                .filter(e -> Integer.parseInt(e[0]) == empNo)
                .map(e -> e[1])
                .collect(Collectors.toList());
    }

    // -----------------------------------------
    // obtener salarios de un empleado usando Streams
    // -----------------------------------------
    public static List<Integer> obtenerSalariosPorEmpleado(int empNo) {
        List<String[]> todos = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT emp_no, salary FROM salaries")) {

            while (rs.next()) {
                todos.add(new String[]{ rs.getString("emp_no"), rs.getString("salary") });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return todos.stream()
                .filter(s -> Integer.parseInt(s[0]) == empNo)
                .map(s -> Integer.parseInt(s[1]))
                .collect(Collectors.toList());
    }

    // -----------------------------------------
    // verificar si un empleado es gerente usando Streams
    // -----------------------------------------
    public static boolean esGerente(int empNo) {
        List<Integer> gerentes = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT emp_no FROM dept_manager")) {

            while (rs.next()) {
                gerentes.add(rs.getInt("emp_no"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gerentes.stream().anyMatch(e -> e == empNo);
    }

    // -----------------------------------------
    // obtener un solo empleado con detalles usando Streams
    // -----------------------------------------
    public static Empleado obtenerEmpleadoConDetalles(int empNo) {
        List<Empleado> todos = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                Empleado e = new Empleado(
                        rs.getInt("emp_no"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("hire_date")
                );
                todos.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return todos.stream()
                .filter(e -> e.getEmpNo() == empNo)
                .findFirst()
                .orElse(null);
    }

    // -----------------------------------------
// obtener un empleado por ID
// -----------------------------------------
    public static Empleado obtenerEmpleadoPorId(int empNo) {
        Empleado empleado = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT * FROM employees WHERE emp_no = ?")) {

            stmt.setInt(1, empNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empleado = new Empleado(
                            rs.getInt("emp_no"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("gender"),
                            rs.getString("hire_date")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return empleado;
    }


}


