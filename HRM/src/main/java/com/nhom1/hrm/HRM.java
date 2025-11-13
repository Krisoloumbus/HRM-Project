/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nhom1.hrm;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.nhom1.hrm.SQL.ConnectSQL;
import com.nhom1.hrm.SQL.Table;
import com.nhom1.hrm.SQL.UserDAO;
import com.nhom1.hrm.UI.AppShell;
import com.nhom1.hrm.UI.AuthProvider;
import com.nhom1.hrm.UI.LogInDialog;
import com.nhom1.hrm.UI.LoginController;

/**
 *
 * @author Kris
 */
public class HRM {
    public static void main(String[] args) {
        /*try (Connection c = ConnectSQL.getConnection()) {
            Table.createEmpIfNotHave(c);
            Table.createUserIfNotHave(c);
            javax.swing.JOptionPane.showMessageDialog(null, "Connected to DB");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Unable to create Table or connecte to DB: " + e.getMessage());
        }

        // Launch GUI
        SwingUtilities.invokeLater(() -> new AppShell().setVisible(true));
        */

         SwingUtilities.invokeLater(() -> {
            try (var con = ConnectSQL.getConnection()) {
                // Tạo bảng nhân sự của bạn như cũ
                Table.createEmpIfNotHave(con);
                // Đảm bảo có bảng Users
                Table.createUserIfNotHave(con);

                // (Optional/Testing) seed admin if not have
                if (!UserDAO.check(con, "admin", "123".toCharArray())) {
                    // Failed check due to no user, try to create
                    UserDAO.createUser(con, "admin", "123".toCharArray());
                }

                // Login dialog
                AuthProvider auth = (u, p) -> {
                    try { return UserDAO.check(con, u, p); }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Auth error: " + ex.getMessage());
                        return false;
                    }
                };
                LogInDialog dlg = new LogInDialog(null, true);
            

                LoginController ctrl = new LoginController(dlg, auth);
                if (!ctrl.showDialog()) {
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
