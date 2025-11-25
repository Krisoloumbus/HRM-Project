/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nhom1.hrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.nhom1.hrm.SQL.ConnectSQL;
import com.nhom1.hrm.SQL.Table;
import com.nhom1.hrm.SQL.UpdateTable;
import com.nhom1.hrm.SQL.UserDAO;
import com.nhom1.hrm.UI.AppShell;
import com.nhom1.hrm.UI.AuthProvider;
import com.nhom1.hrm.UI.LoginController;
import com.nhom1.hrm.UI.UpdateUI;

/**
 *
 * @author Kris
 */
public class HRM {
    public static void main(String[] args) {
        UpdateUI.LaFUI();
        SwingUtilities.invokeLater(() -> {
            try {
                Supplier<Connection> connFactory = () -> {
                    try {
                    return ConnectSQL.getConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Connection error: ", e);
                }
                };
                try (Connection con = connFactory.get()) {
                UpdateTable.runAllMigrations(con);
                // Tạo bảng nhân sự của bạn như cũ
                Table.createEmpIfNotHave(con);
                // Đảm bảo có bảng Users
                Table.createUserIfNotHave(con);

                // (Optional/Testing) seed admin if not have
                if (!UserDAO.check(con, "admin", "123".toCharArray())) {
                    // Failed check due to no user, try to create
                    UserDAO.createUser(con, "admin", "123".toCharArray());
                }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "DB error: " + e.getMessage());
            }
            
            // Login dialog
            AuthProvider auth = (u, p) -> {
                try (Connection con = connFactory.get()) { return UserDAO.check(con, u, p); }
                catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Auth error: " + ex.getMessage());
                    return false;
                }
            };

            boolean ok = LoginController.showDialog(null, auth);
            if (!ok) {
                System.exit(0);
            }

            AppShell app = new AppShell();
            app.getRootPane().putClientProperty("auth", auth);
            app.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "DB error: " + e.getMessage());
            }
        });
    }
}
