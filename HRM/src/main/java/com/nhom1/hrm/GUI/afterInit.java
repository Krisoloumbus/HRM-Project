package com.nhom1.hrm.GUI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.nhom1.hrm.models.Department;
import com.nhom1.hrm.models.Education;
import com.nhom1.hrm.models.Gender;
import com.nhom1.hrm.models.JobLevel;

public final class afterInit {
    private afterInit(){}
    public static void setgetComboBox(JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox, JComboBox<Education> eduBox,
    JComboBox<Gender> genderBox, JTable eTable, JButton addButton, JButton delButton)
    {
        genderBox.setModel(new DefaultComboBoxModel<>(Gender.values()));
        deptBox.setModel(new DefaultComboBoxModel<>(Department.values()));
        lvlBox.setModel(new DefaultComboBoxModel<>(JobLevel.values()));
        eduBox.setModel(new DefaultComboBoxModel<>(Education.values()));

        if (genderBox.getItemCount() > 0) genderBox.setSelectedIndex(0);
        if (deptBox.getItemCount() > 0) deptBox.setSelectedIndex(0);
        if (lvlBox.getItemCount() > 0) lvlBox.setSelectedIndex(0);
        if (eduBox.getItemCount() > 0) eduBox.setSelectedIndex(0);

        eTable.setRowSelectionAllowed(true);
        eTable.setColumnSelectionAllowed(false);
        eTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        eTable.setSelectionBackground(new java.awt.Color(220,220,220));
        eTable.setSelectionForeground(java.awt.Color.BLACK);

        delButton.setEnabled(false);
        eTable.getSelectionModel().addListSelectionListener(e -> delButton.setEnabled(eTable.getSelectedRowCount() > 0));
    }
}