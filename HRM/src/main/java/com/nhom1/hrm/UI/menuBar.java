package com.nhom1.hrm.UI;

import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.nhom1.hrm.models.Department;
import com.nhom1.hrm.models.Education;
import com.nhom1.hrm.models.Gender;
import com.nhom1.hrm.models.JobLevel;


public class menuBar {

    public static void aboutSwitchCardOnClick(JMenuItem aboutItem, cards deck, String cardName) {
        aboutItem.addActionListener(e -> deck.showCard(cardName));
    }

    public static String onSelected(
            JTable eTable,
            JTextField nameField,
            JTextField dateField,          //I gonna comeback for this later
            JComboBox<Education> eduBox,
            JComboBox<Department> deptBox,
            JComboBox<JobLevel> lvlBox,
            JComboBox<Gender> genderBox,
            JTextField phoneField,
            JTextField mailField,
            JTextField salaryField) {
        int viewRow = eTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Select one row in table");
            return null;
        }
        int row = eTable.convertRowIndexToModel(viewRow);
        DefaultTableModel model = (DefaultTableModel) eTable.getModel();
        //Only reading EID column
        String eid = model.getValueAt(row, 1).toString();
        nameField.setText(String.valueOf(model.getValueAt(row, 2)));
        genderBox.setSelectedItem((Gender) model.getValueAt(row, 3));
        eduBox.setSelectedItem((Education) model.getValueAt(row, 4));
        phoneField.setText(String.valueOf(model.getValueAt(row, 5)));
        Object emailObj = model.getValueAt(row, 6);
        mailField.setText(emailObj != null ? emailObj.toString() : "");
        deptBox.setSelectedItem((Department) model.getValueAt(row, 7));
        lvlBox.setSelectedItem((JobLevel) model.getValueAt(row, 8));
        salaryField.setText(String.valueOf(model.getValueAt(row, 9)));
        //Empty for now
        dateField.setText("");
        return eid;
    }

    public static void onExport(AppShell aThis, JTable eTable) {
    JFileChooser chooser = new JFileChooser();
    if (chooser.showSaveDialog(aThis) == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getParentFile(), file.getName() + ".csv");
        }
        try {
            function.exportCSV(eTable, file);
            JOptionPane.showMessageDialog(aThis, "" + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(aThis, "Export failed: " + e.getMessage());
        }
    }
}
}
