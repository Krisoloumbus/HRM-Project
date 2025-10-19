/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nhom1.hrm;

import javax.swing.SwingUtilities;

import com.nhom1.hrm.GUI.main_gui;
import com.nhom1.hrm.SQL.connectSQL;
import com.nhom1.hrm.SQL.table;
import com.nhom1.hrm.models.Employee;

/**
 *
 * @author Kris
 */
public class HRM extends Employee {
    public static void main(String[] args) {
        try (java.sql.Connection c = connectSQL.getConnection()) {
            table.taobangifchuaco(c);;
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Không thể tạo bảng/ket noi DB: " + e.getMessage());
        }
        // Launch GUI
        SwingUtilities.invokeLater(() -> new main_gui().setVisible(true));
    }
}
