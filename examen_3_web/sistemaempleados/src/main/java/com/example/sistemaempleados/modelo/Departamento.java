package com.example.sistemaempleados.modelo;

public class Departamento {
    private String deptNo;
    private String deptName;

    public Departamento(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    public String getDeptNo() { return deptNo; }
    public String getDeptName() { return deptName; }
}