package com.nhom1.hrm.UI;

import java.sql.Connection;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.nhom1.hrm.SQL.connectSQL;
import com.nhom1.hrm.SQL.middleMan;
import com.nhom1.hrm.models.Department;
import com.nhom1.hrm.models.Education;
import com.nhom1.hrm.models.Employee;
import com.nhom1.hrm.models.Gender;
import com.nhom1.hrm.models.JobLevel;

public final class buttonAtcion {
    private buttonAtcion(){}
    public static void onAdd(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
    JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable)
    {
        if (!function.validateInput(nameField, eduBox, genderBox, deptBox, lvlBox, emailField, phoneField, salaryField)) return;
        var emp = function.newEmployeeToDB(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField);
        try (Connection c = com.nhom1.hrm.SQL.connectSQL.getConnection()) {
            com.nhom1.hrm.SQL.table.taobangifchuaco(c);
            new com.nhom1.hrm.SQL.middleMan().insert(c, emp);
            JOptionPane.showMessageDialog(null, "Đã thêm nhân viên!");
            guiTable.loadTable(eTable);
            function.setInput(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi thêm: " + ex.getMessage());
        }
    }
    
    public static void onDelete(JTable eTable)
    {int[] sel = eTable.getSelectedRows();
        if (sel == null || sel.length == 0) {
            JOptionPane.showMessageDialog(null, "Hãy chọn ít nhất một dòng để xóa.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(null, "Xóa " + sel.length + " bản ghi?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;
        try (Connection c = com.nhom1.hrm.SQL.connectSQL.getConnection()) {
            var middleMan = new com.nhom1.hrm.SQL.middleMan();
            Arrays.sort(sel);
            for (int i = sel.length - 1; i >= 0; i--) {
                int viewRow  = sel[i];
                int modelRow = eTable.convertRowIndexToModel(viewRow);
                Object idObj = eTable.getModel().getValueAt(modelRow, 1); // cột 1 = EID (No ở cột 0)
                String eid   = (idObj != null) ? idObj.toString() : null;
                if (eid != null && !eid.isBlank()) middleMan.delByEID(c, eid);
            }
            JOptionPane.showMessageDialog(null, "Đã xóa!");
            guiTable.loadTable(eTable);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi xóa: " + ex.getMessage());
        }
    }

    //testing
    public static void onUpdate(
        String eid, JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
        JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable){
        if (eid == null || eid.isBlank()) {
            JOptionPane.showMessageDialog(null, "Không có nhân viên nào đang được chọn để cập nhật.");
            return;
        }
        if (!function.validateInput(nameField, eduBox, genderBox, deptBox, lvlBox, emailField, phoneField, salaryField)) return;

        Employee emp = function.existingEmployeeFromForm(eid, nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField);

        try (Connection c = connectSQL.getConnection()) {
            new middleMan().update(c, emp);
            JOptionPane.showMessageDialog(null, "Đã cập nhật nhân viên!");

            //For some unknwon reason, I can't reload table with this line
            //guiTable.loadTable(eTable);

            /*Alter way*/
            int viewRow = eTable.getSelectedRow();
            if (viewRow != -1) {
                int row = eTable.convertRowIndexToModel(viewRow);
                DefaultTableModel dfModel = (DefaultTableModel) eTable.getModel();
                dfModel.setValueAt(emp.getName(),          row, 2); // Full Name
                dfModel.setValueAt(emp.getGender(),        row, 3); // Gender (enum)
                dfModel.setValueAt(emp.getEdu(),           row, 4); // Education
                dfModel.setValueAt(emp.getPhone(),         row, 5); // Phone
                dfModel.setValueAt(emp.getEmail(),         row, 6); // Email
                dfModel.setValueAt(emp.getDepartment(),    row, 7); // Department
                dfModel.setValueAt(emp.getLevel(),         row, 8); // Level
                dfModel.setValueAt(emp.getSalary(),        row, 9); // Salary
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi cập nhật: " + ex.getMessage());
        }
    }
}
