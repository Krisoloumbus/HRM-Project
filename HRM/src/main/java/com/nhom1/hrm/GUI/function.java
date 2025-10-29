package com.nhom1.hrm.GUI;

import java.math.BigDecimal;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.nhom1.hrm.models.Department;
import com.nhom1.hrm.models.Education;
import com.nhom1.hrm.models.Employee;
import com.nhom1.hrm.models.Gender;
import com.nhom1.hrm.models.JobLevel;

public final class function {
    private function(){}
    public static boolean validateInput(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Gender> genderBox,
       JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox, JTextField maField,
       JTextField phoneField, JTextField salaryField) {
        if (nameField.getText().isBlank()
            || maField.getText().isBlank()
            || genderBox.getSelectedItem() == null
            || eduBox.getSelectedItem() == null
            || deptBox.getSelectedItem() == null
            || lvlBox.getSelectedItem() == null
            || salaryField.getText().isBlank()
            || phoneField.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đủ thông tin.");
            return false;
        }
        try { new BigDecimal(salaryField.getText().trim()); }
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(null,"Invalid Salary"); return false; }
        try { Long.parseLong(phoneField.getText().trim()); }
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(null,"Phone must be number"); return false; }
        //try {} catch ( e) {}
        return true;
    }

    public static Employee newEmployeeToDB(JTextField nameField, JComboBox<Education> eduBox,
    JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox, JComboBox<Gender> genderBox,
    JTextField phoneField, JTextField emailField, JTextField salaryField)
    {
        var e = new com.nhom1.hrm.models.Employee();
        e.setName(nameField.getText().trim());
        e.setEdu((Education) eduBox.getSelectedItem());
        e.setGender((Gender) genderBox.getSelectedItem());
        e.setDepartment((Department) deptBox.getSelectedItem());
        e.setLevel((JobLevel) lvlBox.getSelectedItem());
        e.setPhone(phoneField.getText().trim());
        var emailTxt = emailField.getText().trim();
        e.setEmail(emailTxt.isEmpty() ? null : emailTxt);
        var salaryTxt = salaryField.getText().trim();
        e.setSalary(salaryTxt.isEmpty() ? BigDecimal.ZERO : new BigDecimal(salaryTxt));
        return e;
    }

    public static void setInput(JTextField nameField, JComboBox<Education> eduBox, 
    JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox, JComboBox<Gender> genderBox,
    JTextField phoneField, JTextField emailField,JTextField salaryField) 
    {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        salaryField.setText("");
        if (genderBox.getItemCount()>0) genderBox.setSelectedIndex(0);
        if (deptBox.getItemCount() > 0) deptBox.setSelectedIndex(0);
        if (lvlBox.getItemCount()  > 0) lvlBox.setSelectedIndex(0);
        if (eduBox.getItemCount()  > 0) eduBox.setSelectedIndex(0);

    }
}

