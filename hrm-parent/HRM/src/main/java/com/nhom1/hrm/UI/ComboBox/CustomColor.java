package com.nhom1.hrm.UI.ComboBox;

import javax.swing.JComboBox;

public class CustomColor {
    //Set custom color for many combo box at once
    public static void setCustomRendererForAll(JComboBox<?>... boxes) {
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        for (JComboBox<?> box : boxes) {
            box.setRenderer(renderer);
        }
    }
}
