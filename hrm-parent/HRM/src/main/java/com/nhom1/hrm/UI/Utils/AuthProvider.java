/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI.Utils;

/**
 *
 * @author Kris
 */
public interface AuthProvider {
    boolean authenticate(String username, char[] password);
}
