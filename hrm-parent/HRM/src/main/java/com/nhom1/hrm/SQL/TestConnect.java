/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.SQL;

import java.sql.Connection;

/**
 *
 * @author Kris
 */
public class TestConnect {
    public static void main(String[] args) {
        try (Connection c = ConnectSQL.getConnection()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Connected to DB");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Unable to create Table or connecte to DB: " + e.getMessage());
        }
    }
}
