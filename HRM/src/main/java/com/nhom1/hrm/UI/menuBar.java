package com.nhom1.hrm.UI;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.nhom1.hrm.models.Department;
import com.nhom1.hrm.models.Education;
import com.nhom1.hrm.models.Gender;
import com.nhom1.hrm.models.JobLevel;


public class menuBar {

    public static void aboutSwitchCardOnClick(JMenuItem aboutItem, cards deck, String cardName) {
        aboutItem.addActionListener(e -> deck.showCard(cardName));
    }

    //TESTING
    public String selectedMenuItem(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
    JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable){
        int sel = eTable.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(null, "Hãy chọn ít nhất một dòng để edit.");
            return null;
        }
       int ok = JOptionPane.showConfirmDialog(null, "Edit " + sel + " bản ghi?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return null;
        int modelRow = eTable.convertRowIndexToModel(sel);
        var model = eTable.getModel();

        // cột 1 = EID
        String eid = model.getValueAt(modelRow, 1).toString();

        nameField.setText(String.valueOf(model.getValueAt(modelRow, 2)));
        genderBox.setSelectedItem((Gender) model.getValueAt(modelRow, 3));
        eduBox.setSelectedItem((Education) model.getValueAt(modelRow, 4));
        phoneField.setText(String.valueOf(model.getValueAt(modelRow, 5)));

        Object emailObj = model.getValueAt(modelRow, 6);
        emailField.setText(emailObj != null ? emailObj.toString() : "");

        deptBox.setSelectedItem((Department) model.getValueAt(modelRow, 7));
        lvlBox.setSelectedItem((JobLevel) model.getValueAt(modelRow, 8));
        salaryField.setText(String.valueOf(model.getValueAt(modelRow, 9)));

        // hiện tại chưa lưu ngày sinh xuống DB, tạm để trống
        //dateField.setText("");

        return eid;
    }
}
