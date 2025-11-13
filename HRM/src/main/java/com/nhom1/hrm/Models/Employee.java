/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.Models;

import java.math.BigDecimal;

/**
 *
 * @author Kris
 */
public class Employee {
    private Integer number;
    private String eid;
    private String name;
    private Gender gender;
    private Education edu;
    private Department dept;
    private String email;
    private JobLevel level;
    private String phone;
    private BigDecimal salary;

    public Employee(){}
    public Employee (Integer Number, String EID, String Name, Gender Gender,
    Education Edu, Department Dept, JobLevel Level, String Email,
    String Phone, BigDecimal Salary) {
        this.number = Number;
        this.email = Email;
        this.eid =  EID;
        this.phone = Phone;
        this.name = Name;
        this.edu = Edu;
        this.dept = Dept;
        this.level =Level;
        this.salary = Salary;
        this.gender = Gender;
    }

    // Get
    public Gender getGender() {return gender;}
    public Integer getNo() {return number;}
    public String getEID(){return eid;}
    public String getEmail(){return email;}
    public String getPhone(){return phone;}
    public String getName(){return name;}
    public Education getEdu(){return edu;}
    public Department getDepartment(){return dept;}
    public JobLevel getLevel (){return level;}
    public BigDecimal getSalary (){return salary;}

    // Set
    public void setGender (Gender sGender) {this.gender = sGender;}
    public void setNo (Integer sNumber) {this.number = sNumber;}
    public void setPhone (String sPhone) {this.phone = sPhone;}
    public void setEmail (String sEmail) {this.email = sEmail;}
    public void setEID (String sEID){this.eid = sEID;}
    public void setName (String sName){this.name = sName;}
    public void setEdu (Education sEdu){this.edu = sEdu;}
    public void setDepartment (Department sDept){this.dept = sDept;}
    public void setLevel (JobLevel sLevel){this.level = sLevel;}
    public void setSalary (BigDecimal sSalary){this.salary = sSalary;}
}