/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm;

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
    public long hetLuongcb(){
        return luongcb;
    }

    // Set
    public void setManv (String sManv){
        this.manv = sManv;
    }
    public void setTen (String sTennv){
        this.tennv = sTennv;
    }
    

}
