/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm;

import java.util.Scanner;

/**
 *
 * @author Kris
 */
public abstract class Employee {
    private String eid;
    private String name;
    private Education edu;
    private Department department;
    private Level level;
    private long salary;

    public Employee(){}
    public Employee (String EID, String Name, Education Edu, Department Department, Level Level, Long Salary) {
        this.eid =  EID;
        this.name = Name;
        this.edu = Edu;
        this.department = Department;
        this.level =Level;
        this.salary = Salary;
    }

    // Get
    public String getEID(){
        return eid;
    }
    public String getName(){
        return name;
    }
    public Education getEdu(){
        return edu;
    }
    public Department getDepartment(){
        return department;
    }
    public Level getLevel (){
        return level;
    }
    public Long getSalary (){
        return salary;
    }

    // Set
    public void setEID (String sEID){
        this.eid = sEID;
    }
    public void setName (String sName){
        this.name = sName;
    }
    public void setEdu (Education sEdu){
        this.edu = sEdu;
    }
    public void setDepartment (Department sDepartment){
        this.department = sDepartment;
    }
    public void setLevel (Level sLevel){
        this.level = sLevel;
    }
    public void setSalary (Long sSalary){
        this.salary = sSalary;
    }


    //Input
    public void Input(Scanner sc){
        System.out.println("Enter employee ID/EID: ");
        setEID(sc.nextLine().strip());
        System.out.println("Enter employee ID/EID: ");
        setName(sc.nextLine().strip());
        System.out.println("Enter employee ID/EID: ");
        setEdu(edu);
        System.out.println("Enter employee ID/EID: ");
        setDepartment(department);
        System.out.println("Enter employee ID/EID: ");
        setLevel(level);
        System.out.println("Enter employee ID/EID: ");
        setSalary(sc.nextLong());
        sc.nextLine();
    }    
    //Output
    public void Output(){
        System.out.println("Employee EID: " + getEID() +
        "\n" + "Employee Name: " + getName() +
        "\n" + "Employee Education: " + getEdu() +
        "\n" + "Department: " + getDepartment() +
        "\n" + "Level: " + getLevel() +
        "\n" + "Salary: " + getSalary());
    }
}

