package com.nhom1.hrm.UI;

import javax.swing.JComboBox;
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

    //TESTING
    public static String onSelected(
            JTable eTable,
            JTextField nameField,
            JTextField dateField,          // hiện DB chưa lưu DOB, để trống
            JComboBox<Education> eduBox,
            JComboBox<Department> deptBox,
            JComboBox<JobLevel> lvlBox,
            JComboBox<Gender> genderBox,
            JTextField phoneField,
            JTextField mailField,
            JTextField salaryField) {

        int viewRow = eTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(null, "Hãy chọn một dòng trong bảng.");
            return null;
        }

        int row = eTable.convertRowIndexToModel(viewRow);
        DefaultTableModel model = (DefaultTableModel) eTable.getModel();

        // cột 0 = No, cột 1 = EID → chỉ đọc EID, không sửa
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

        // hiện bạn chưa lưu DOB xuống SQL, tạm để ô rỗng
        dateField.setText("");

        return eid;
    }
}
