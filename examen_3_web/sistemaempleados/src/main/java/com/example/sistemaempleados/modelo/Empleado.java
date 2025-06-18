package com.example.sistemaempleados.modelo;

public class Empleado {
    private int empNo;
    private String firstName;
    private String lastName;
    private String gender;
    private String hireDate;

    public Empleado(int empNo, String firstName, String lastName, String gender, String hireDate) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
    }

    public int getEmpNo() { return empNo; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
    public String getHireDate() { return hireDate; }
}