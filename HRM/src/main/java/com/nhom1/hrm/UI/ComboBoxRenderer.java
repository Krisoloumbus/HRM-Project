package com.nhom1.hrm.UI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class ComboBoxRenderer extends DefaultListCellRenderer {
    //Set color for combo box
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        //Component compo = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        JLabel compo = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        //Allow to change index color
        compo.setOpaque(true);

        if (index == -1) {
            //Ô đang hiển thị trên combobox (lúc chưa xổ xuống)
            //compo.setBackground(new Color(240, 240, 240)); //change color as will
            compo.setBackground(Color.GREEN);
            compo.setForeground(Color.BLACK);
        } else if (isSelected) {
            compo.setBackground(new Color(0, 120, 215)); // màu khi chọn
            compo.setForeground(Color.WHITE);
        } else {
            compo.setBackground(Color.WHITE);            // màu bình thường
            compo.setForeground(Color.BLACK);
        }
        return compo;
    }
}
