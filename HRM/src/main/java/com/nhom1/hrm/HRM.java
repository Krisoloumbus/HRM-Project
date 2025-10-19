/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nhom1.hrm;

import java.util.Scanner;

import com.nhom1.hrm.models.Department;
import com.nhom1.hrm.models.Education;
import com.nhom1.hrm.models.Employee;
import com.nhom1.hrm.models.Level;

/**
 *
 * @author Kris
 */
public class HRM extends Employee {
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) { 
            System.out.println("""
                ==== MENU ====
                1. Add new employee
                2. Show list
                3. Exit
            """);
            System.out.println("Option: ");
            switch (sc.nextLine().strip()) {
                case "1":
                    EInput();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Please choose valid option: ");
                    break;
            }
        }
    }

    //Input
    static void EInput(){
        System.out.print("Enter employee ID/EID: ");
        setEID(sc.nextLine().strip());
        System.out.print("Enter employee Name: ");
        setName(sc.nextLine().strip());
        System.out.print("Enter employee Education: ");
        setEdu(Education.valueOf(sc.nextLine().trim().toUpperCase()));
        System.out.print("Enter employee Department: ");
        setDepartment(Department.valueOf(sc.nextLine().strip().toUpperCase()));
        System.out.print("Enter employee Level: ");
        setLevel(Level.valueOf(sc.nextLine().strip().toUpperCase()));
        System.out.print("Enter employee Salary: ");
        setSalary(sc.nextLong());
        sc.nextLine();
    }    
}
