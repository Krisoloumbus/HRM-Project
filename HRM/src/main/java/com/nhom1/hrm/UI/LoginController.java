/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI;

import java.awt.Frame;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Kris
 */
public final class LoginController {

    private static final String OK_FLAG = "login.ok";

    public static void OnLogin (JDialog viewUI, JTextField userTextField, JPasswordField passwordField, AuthProvider auth){
        String u = userTextField.getText().trim();
        char[] p = passwordField.getPassword();
        try {
            boolean pass = (auth != null && auth.authenticate(u, p));
            if (pass) {
                viewUI.getRootPane().putClientProperty(OK_FLAG, Boolean.TRUE);
                JOptionPane.showMessageDialog(viewUI, "Welcome to HRM user " + u);
                viewUI.dispose();
            } else {
                JOptionPane.showMessageDialog(viewUI, "Invalid user/password");
            }
        } finally {
            Arrays.fill(p, '\0');
        }
    }

    public static void OnCancel (JDialog viewUI) {
        JOptionPane.showMessageDialog(viewUI, "Good Bye!");
        viewUI.dispose();
        //System.exit(0);
    }
    
    public static boolean showDialog(Frame owner, AuthProvider auth) {
        LogInDialog dlg = new LogInDialog(owner, true); // modal
        // Lưu auth vào clientProperty để handler lấy lại nếu bạn muốn
        dlg.getRootPane().putClientProperty("auth", auth);
        dlg.setLocationRelativeTo(owner);
        dlg.setVisible(true); // block đến khi dispose()

        Object v = dlg.getRootPane().getClientProperty(OK_FLAG);
        return (v instanceof Boolean) && (Boolean) v;
    }
}
