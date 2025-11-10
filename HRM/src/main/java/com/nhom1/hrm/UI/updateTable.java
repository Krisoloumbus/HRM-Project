/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI;

import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 *
 * @author Kris
 */
public class updateTable {
    public static void addAd_dressCol(Connection c) {
        String sql = """
                IF COL_LENGTH('Employees', 'Ad_Dress') IS NULL
            BEGIN
                ALTER TABLE Employees
                ADD Ad_Dress NVARCHAR (500);
            END
                """;
        try (Statement st = c.createStatement()) {
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Update table error: " + e.getMessage());
        }
    }
}
