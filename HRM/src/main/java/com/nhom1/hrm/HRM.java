/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nhom1.hrm;

import java.sql.Connection;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.nhom1.hrm.SQL.connectSQL;
import com.nhom1.hrm.SQL.table;
import com.nhom1.hrm.SQL.userDAO;
import com.nhom1.hrm.UI.AppShell;
import com.nhom1.hrm.UI.AuthProvider;
import com.nhom1.hrm.UI.logInDialog;
import com.nhom1.hrm.models.Employee;

/**
 *
 * @author Kris
 */
public class HRM extends Employee {
    public static void main(String[] args) {
        /*try (Connection c = connectSQL.getConnection()) {
            table.createEmpIfNotHave(c);
            table.createUserIfNotHave(c);
            javax.swing.JOptionPane.showMessageDialog(null, "Connected to DB");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Unable to create table or connecte to DB: " + e.getMessage());
        }

        // Launch GUI
        SwingUtilities.invokeLater(() -> new AppShell().setVisible(true));
        */

         SwingUtilities.invokeLater(() -> {
            try (var con = connectSQL.getConnection()) {
                // Tạo bảng nhân sự của bạn như cũ
                table.createEmpIfNotHave(con);
                // Đảm bảo có bảng Users
                table.createUserIfNotHave(con);

                // (tùy chọn) seed admin nếu chưa có
                if (!userDAO.check(con, "admin", "123".toCharArray())) {
                    // Nếu check thất bại do không có user, ta thử tạo
                    userDAO.createUser(con, "admin", "123".toCharArray());
                }

                // Login dialog
                AuthProvider auth = (u, p) -> {
                    try { return userDAO.check(con, u, p); }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Auth error: " + ex.getMessage());
                        return false;
                    }
                };
                logInDialog dlg = new logInDialog(null, false);
                dlg.setVisible(true);
                if (!dlg.isOk()) {
                    System.exit(0);
                }

                new AppShell().setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "DB error: " + e.getMessage());
            }
        });
    }
}
