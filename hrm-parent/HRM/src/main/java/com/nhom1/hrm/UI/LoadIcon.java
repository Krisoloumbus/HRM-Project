/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI;

import java.awt.Image;
import java.net.URL;
import java.util.Objects;

import javax.swing.ImageIcon;

/**
 *
 * @author Kris
 */
public class LoadIcon {
    /*public static ImageIcon getResources(String name) {
        URL u = LogInDialog.class.getResource("/com/nhom1/hrm/Asset/" + name);
        return new ImageIcon(Objects.requireNonNull(u, "Missing resource: " + name));
    }*/

    /*private static final String BASE = "/Asset/";
    public static ImageIcon get(String file) {
        java.net.URL u = LoadIcon.class.getResource(BASE + file);
        if (u == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Missing: " + BASE + file);
            return new ImageIcon();
        }
        return new ImageIcon(u);
    }*/

    public static URL url(String path) {
        return Objects.requireNonNull(
            LoadIcon.class.getResource(path),
            "Missing resource: " + path
        );
    }

    /** Lấy ImageIcon theo tên trong thư mục Asset/ */
    public static ImageIcon icon(String name) {
        return new ImageIcon(url("/Asset/" + name));
    }

    public static Image image(String name) {
        return icon(name).getImage();
    }
}
