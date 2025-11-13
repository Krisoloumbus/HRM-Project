/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nhom1.hrm;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.nhom1.hrm.SQL.connectSQL;
import com.nhom1.hrm.SQL.table;
import com.nhom1.hrm.SQL.userDAO;
import com.nhom1.hrm.UI.AppShell;
import com.nhom1.hrm.UI.AuthProvider;
import com.nhom1.hrm.UI.logInController;
import com.nhom1.hrm.UI.logInDialog;

/**
 *
 * @author Kris
 */
public class HRM {
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

                // (Optional/Testing) seed admin if not have
                if (!userDAO.check(con, "admin", "123".toCharArray())) {
                    // Failed check due to no user, try to create
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
                logInDialog dlg = new logInDialog(null, true);
            

                logInController ctrl = new logInController(dlg, auth);
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
