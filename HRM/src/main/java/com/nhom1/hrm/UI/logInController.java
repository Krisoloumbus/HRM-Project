/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI;

import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 *
 * @author Kris
 */
public final class logInController {
    private final logInDialog viewUI;
    private final AuthProvider auth;
    private boolean ok = false;

    public logInController(logInDialog viewUI, AuthProvider auth){
        this.viewUI = viewUI;
        this.auth = auth;
        
        //set event for 2 viewUI(loginDialog) button through 2 methods below
        this.viewUI.setOnLogin(e -> OnLogin());
        this.viewUI.setOnCancel(e -> viewUI.dispose());
    }

    private void OnLogin (){
        String u = viewUI.getUsernameText();
        char[] p = viewUI.getPasswordChars();
        try {
            if (auth != null && auth.authenticate(u, p)) {
                ok = true;
                viewUI.dispose();
            } else {
                JOptionPane.showMessageDialog(viewUI, "Invalid user/password");
            }
        } finally {
            Arrays.fill(p, '\0');
        }
    }
    
    public boolean showDialog() {
        viewUI.setLocationRelativeTo(null);
        viewUI.setModal(true);                //make sure modal turn on and user has to login before can do anything
        //viewUI.getRootPane().setDefaultButton(viewUI.getRootPane().getDefaultButton()); // optional
        viewUI.setVisible(true);
        return ok;
    }

}
