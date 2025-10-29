/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nhom1.hrm;

import java.sql.Connection;

import javax.swing.SwingUtilities;

import com.nhom1.hrm.GUI.mainGui;
import com.nhom1.hrm.SQL.connectSQL;
import com.nhom1.hrm.SQL.table;
import com.nhom1.hrm.models.Employee;

/**
 *
 * @author Kris
 */
public class HRM extends Employee {
    public static void main(String[] args) {
        try (Connection c = connectSQL.getConnection()) {
            table.taobangifchuaco(c);
            javax.swing.JOptionPane.showMessageDialog(null, "Connected to Data");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Không thể tạo bảng/ket noi DB: " + e.getMessage());
        }
        // Launch GUI
        SwingUtilities.invokeLater(() -> new mainGui().setVisible(true));
    }
}
