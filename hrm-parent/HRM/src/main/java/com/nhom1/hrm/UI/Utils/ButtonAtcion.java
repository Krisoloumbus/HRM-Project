package com.nhom1.hrm.UI.Utils;

import com.nhom1.hrm.UI.Utils.UiTable;
import com.nhom1.hrm.UI.Utils.Function;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.nhom1.hrm.Models.Department;
import com.nhom1.hrm.Models.Education;
import com.nhom1.hrm.Models.Employee;
import com.nhom1.hrm.Models.Gender;
import com.nhom1.hrm.Models.JobLevel;
import com.nhom1.hrm.SQL.ConnectSQL;
import com.nhom1.hrm.SQL.DAO.EmpDAO;
import com.nhom1.hrm.SQL.Table.Table;

public final class ButtonAtcion {
    private ButtonAtcion(){}
    public static void onAdd(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
    JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable, JTextField ad_dressField)
    {
        if (!Function.validateInput(nameField, eduBox, genderBox, deptBox, lvlBox, emailField, phoneField, salaryField, ad_dressField)) return;
        var emp = Function.newEmployeeToDB(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField, ad_dressField);
        try (Connection c = ConnectSQL.getConnection()) {
            Table.createEmpIfNotHave(c);
            new EmpDAO().insert(c, emp);
            JOptionPane.showMessageDialog(null, "Employee Add!");
            UiTable.loadTable(eTable);
            Function.resetInput(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField, ad_dressField);
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
        try (Connection c = ConnectSQL.getConnection()) {
            var middleMan = new EmpDAO();
            Arrays.sort(sel);
            for (int i = sel.length - 1; i >= 0; i--) {
                int viewRow  = sel[i];
                int modelRow = eTable.convertRowIndexToModel(viewRow);
                Object idObj = eTable.getModel().getValueAt(modelRow, 1); // cột 1 = EID (No ở cột 0)
                String eid   = (idObj != null) ? idObj.toString() : null;
                if (eid != null && !eid.isBlank()) middleMan.delByEID(c, eid);
            }
            JOptionPane.showMessageDialog(null, "Deleted!");
            UiTable.loadTable(eTable);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Delete error: " + ex.getMessage());
        }
    }

    //testing
    public static void onUpdate(
        String eid, JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
        JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable, JTextField ad_dressField){
        if (eid == null || eid.isBlank()) {
            JOptionPane.showMessageDialog(null, "There is no selected employee to be updated");
            return;
        }
        if (!Function.validateInput(nameField, eduBox, genderBox, deptBox, lvlBox, emailField, phoneField, salaryField, ad_dressField)) return;
        Employee emp = Function.existingEmployeeFromDB(eid, nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField, ad_dressField);
        try (Connection c = ConnectSQL.getConnection()) {
            new EmpDAO().update(c, emp);
            JOptionPane.showMessageDialog(null, "Employee updated!");
            UiTable.loadTable(eTable);
            Function.resetInput(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField, ad_dressField);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Update error: " + ex.getMessage());
        }
    }

    //Searching
    public static void onSearch(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
        JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable, JTextField ad_dressField) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String ad_dress = ad_dressField.getText().trim();

        Gender gender = (Gender) genderBox.getSelectedItem();
        Education edu = (Education) eduBox.getSelectedItem();
        Department dept = (Department) deptBox.getSelectedItem();
        JobLevel lvl = (JobLevel) lvlBox.getSelectedItem();

        boolean noFilter = name.isBlank()
        && phone.isBlank()
        && email.isBlank()
        && ad_dress.isBlank()
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
        try (Connection c = ConnectSQL.getConnection()) {
            EmpDAO mm = new EmpDAO();
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
                        e.getSalary(),
                        e.getAddress()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Search error: " + ex.getMessage());
        }
    }

    //Refresh
    public  static void onRefresh(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox,
    JComboBox<Gender>genderBox, JTextField phoneField, JTextField emailField, JTextField salaryField, JTable eTable, JTextField ad_dressField)
    {
        boolean noFilter = nameField.getText().isBlank()
        && emailField.getText().isBlank()
        && phoneField.getText().isBlank()
        && salaryField.getText().isBlank()
        && ad_dressField.getText().isBlank()
        && genderBox.getSelectedItem() == Gender.Default
        && eduBox.getSelectedItem()    == Education.Default
        && deptBox.getSelectedItem()   == Department.Default
        && lvlBox.getSelectedItem()    == JobLevel.Default;
        if (!noFilter) {
            try {
                Function.resetInput(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField, ad_dressField);
                UiTable.loadTable(eTable);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Refresh error: " + ex.getMessage());
            } return;
        } else {
            try {
                UiTable.loadTable(eTable);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Refresh error: " + ex.getMessage());
            } return;
        }           
    }
}
