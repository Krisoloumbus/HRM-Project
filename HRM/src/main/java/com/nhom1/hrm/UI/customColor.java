package com.nhom1.hrm.UI;

import javax.swing.JComboBox;

public class customColor {
    //Set custom color for many combo box at once
    public static void setCustomRendererForAll(JComboBox<?>... boxes) {
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        for (JComboBox<?> box : boxes) {
            box.setRenderer(renderer);
        }
    }
}
