package com.nhom1.hrm.UI;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.nhom1.hrm.SQL.connectSQL;
import com.nhom1.hrm.SQL.empDAO;
import com.nhom1.hrm.SQL.table;
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
        try (Connection c = connectSQL.getConnection()) {
            table.createEmpIfNotHave(c);
            new empDAO().insert(c, emp);
            JOptionPane.showMessageDialog(null, "Employee Add!");
            uiTable.loadTable(eTable);
            function.resetInput(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Add error: " + ex.getMessage());
        }
    }
    
    public static void onDelete(JTable eTable)
    {int[] sel = eTable.getSelectedRows();
        if (sel == null || sel.length == 0) {
            JOptionPane.showMessageDialog(null, "Select at least one line to delete");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(null, "Delete " + sel.length + " record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;
        try (Connection c = connectSQL.getConnection()) {
            var middleMan = new empDAO();
            Arrays.sort(sel);
            for (int i = sel.length - 1; i >= 0; i--) {
                int viewRow  = sel[i];
                int modelRow = eTable.convertRowIndexToModel(viewRow);
                Object idObj = eTable.getModel().getValueAt(modelRow, 1); // cột 1 = EID (No ở cột 0)
                String eid   = (idObj != null) ? idObj.toString() : null;
                if (eid != null && !eid.isBlank()) middleMan.delByEID(c, eid);
            }
            JOptionPane.showMessageDialog(null, "Deleted!");
            uiTable.loadTable(eTable);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Delete error: " + ex.getMessage());
        }
    }

    //testing
    public static void onUpdate(
        String eid, JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
        JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable){
        if (eid == null || eid.isBlank()) {
            JOptionPane.showMessageDialog(null, "There is no selected employee to be updated");
            return;
        }
        if (!function.validateInput(nameField, eduBox, genderBox, deptBox, lvlBox, emailField, phoneField, salaryField)) return;
        Employee emp = function.existingEmployeeFromDB(eid, nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField);
        try (Connection c = connectSQL.getConnection()) {
            new empDAO().update(c, emp);
            JOptionPane.showMessageDialog(null, "Employee updated!");
            uiTable.loadTable(eTable);
            function.resetInput(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Update error: " + ex.getMessage());
        }
    }

    //Searching
    public static void onSearch(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
        JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        Gender gender = (Gender) genderBox.getSelectedItem();
        Education edu = (Education) eduBox.getSelectedItem();
        Department dept = (Department) deptBox.getSelectedItem();
        JobLevel lvl = (JobLevel) lvlBox.getSelectedItem();

        boolean noFilter = name.isBlank()
        && phone.isBlank()
        && email.isBlank()
        && salaryField.getText().isBlank()
        && gender == Gender.Default
        && edu    == Education.Default
        && dept   == Department.Default
        && lvl    == JobLevel.Default;

        if (noFilter) {
            JOptionPane.showMessageDialog(null, """
                                                No search field has been selected and no value has been entered.
                                                Please select a field and enter a value to search.""");
            return;
        }
        try (Connection c = connectSQL.getConnection()) {
            empDAO mm = new empDAO();
            List<Employee> results = mm.searchEmployees(c, name, gender, edu, lvl, dept, phone, email);
            DefaultTableModel dfModel = (DefaultTableModel) eTable.getModel();
            dfModel.setRowCount(0);

            int no = 1;
            for (Employee e : results) {
                dfModel.addRow(new Object[]{
                        no ++,
                        e.getEID(),
                        e.getName(),
                        e.getGender(),
                        e.getEdu(),
                        e.getPhone(),
                        e.getEmail(),
                        e.getDepartment(),
                        e.getLevel(),
                        e.getSalary()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Search error: " + ex.getMessage());
        }
    }

    //Refresh
    public  static void onRefresh(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
    JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable)
    {
        boolean noFilter = nameField.getText().isBlank()
        && emailField.getText().isBlank()
        && phoneField.getText().isBlank()
        && salaryField.getText().isBlank()
        && genderBox.getSelectedItem() == Gender.Default
        && eduBox.getSelectedItem()    == Education.Default
        && deptBox.getSelectedItem()   == Department.Default
        && lvlBox.getSelectedItem()    == JobLevel.Default;
        if (!noFilter) {
            try {
                function.resetInput(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField);
                uiTable.loadTable(eTable);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Refresh error: " + ex.getMessage());
            } return;
        } else {
            try {
                uiTable.loadTable(eTable);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Refresh error: " + ex.getMessage());
            } return;
        }           
    }

    public static void onLogin(JTextField userTextField, JPasswordField userPasswordField) {
        AuthProvider auth;
        boolean ok;
        String u = userTextField.getText().trim();
        char[] p = userPasswordField.getPassword();
        try {
            if (auth != null && auth.authenticate(u, p)) {
                ok = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Sai user/password");
            }
        } finally {
            Arrays.fill(p, '\0'); // xoá password khỏi RAM
        }
    }

    public static void onCancel() {
        
        doDispose();
    }
}
