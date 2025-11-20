/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI.Utils;

import java.awt.Image;
import java.net.URL;
import java.util.Objects;

import javax.swing.Icon;
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

    private LoadIcon() {}

    /** Lấy URL trong classpath (ném lỗi nếu không thấy) */
    public static URL url(String path) {
        return Objects.requireNonNull(
                LoadIcon.class.getResource(path),
                "Missing resource: " + path
        );
    }

    /** Lấy ImageIcon từ thư mục Asset/ */
    public static ImageIcon icon(String name) {
        return new ImageIcon(url("/Asset/" + name));
    }

    /** Lấy Image từ thư mục Asset/ */
    public static Image image(String name) {
        return icon(name).getImage();
    }

    /** Icon đã scale, tiện cho button/menu (tùy chọn) */
    public static Icon iconScaled(String name, int w, int h) {
        Image img = image(name).getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
