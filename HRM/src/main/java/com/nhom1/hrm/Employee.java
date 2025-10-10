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
    public Employee (String EID, String Name, Education Edu, Long Salary) {
        this.eid =  EID;
        this.name = Name;
        this.edu = Edu;
        this.salary = Salary;
    }

    // Get
    public String getManv(){
        return manv;
    }
    public String getTen(){
        return tennv;
    }
    public String getHocVan(){
        return hocvan;
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
