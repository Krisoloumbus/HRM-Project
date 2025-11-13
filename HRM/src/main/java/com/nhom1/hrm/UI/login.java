/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Kris
 */
public class login {
    public static void getUser(JTextField userField) {
        userField.getText().trim();
    }

    public static void getPassword(JPasswordField passwordField){
        passwordField.getPassword().toString();
    }
}
