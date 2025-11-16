/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI;

import java.net.URL;
import java.util.Objects;

import javax.swing.ImageIcon;

/**
 *
 * @author Kris
 */
public class loadIcon {
    public static ImageIcon getResources(String name) {
        URL u = LogInDialog.class.getResource("/com/nhom1/hrm/Asset/" + name);
        return new ImageIcon(Objects.requireNonNull(u, "Missing resource: " + name));
    }
}
