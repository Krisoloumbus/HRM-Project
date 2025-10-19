/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.models;

/**
 *
 * @author Kris
 */
public abstract class Employee {
    private String eid;
    private String name;
    private Education edu;
    private Department dept;
    private Level level;
    private long salary;

    public Employee(){}
    public Employee (String EID, String Name, Education Edu, Department Dept, Level Level, Long Salary) {
        this.eid =  EID;
        this.name = Name;
        this.edu = Edu;
        this.dept = Dept;
        this.level =Level;
        this.salary = Salary;
    }

    // Get
    public String getEID(){return eid;}
    public String getName(){return name;}
    public Education getEdu(){return edu;}
    public Department getDepartment(){return dept;}
    public Level getLevel (){return level;}
    public Long getSalary (){return salary;}

    // Set
    public void setEID (String sEID){this.eid = sEID;}
    public void setName (String sName){this.name = sName;}
    public void setEdu (Education sEdu){this.edu = sEdu;}
    public void setDepartment (Department sDept){this.dept = sDept;}
    public void setLevel (Level sLevel){this.level = sLevel;}
    public void setSalary (Long sSalary){this.salary = sSalary;}
     
    //Output
    public void EOutput(){
        System.out.print("Employee EID: " + getEID() +
        "\n" + "Employee Name: " + getName() +
        "\n" + "Employee Education: " + getEdu() +
        "\n" + "Department: " + getDepartment() +
        "\n" + "Level: " + getLevel() +
        "\n" + "Salary: " + getSalary());
    }
}

