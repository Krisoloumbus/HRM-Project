package com.nhom1.hrm.UI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nhom1.hrm.Models.Department;
import com.nhom1.hrm.Models.Education;
import com.nhom1.hrm.Models.Employee;
import com.nhom1.hrm.Models.Gender;
import com.nhom1.hrm.Models.JobLevel;

public final class Function {
    private Function(){}
    public static boolean validateInput(JTextField nameField, JComboBox<Education> eduBox, JComboBox<Gender> genderBox,
       JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox, JTextField mailField,
       JTextField phoneField, JTextField salaryField) {
        if (nameField.getText().isBlank()
            || mailField.getText().isBlank()
            || genderBox.getSelectedItem() == Gender.Default
            || eduBox.getSelectedItem() == Education.Default
            || deptBox.getSelectedItem() == Department.Default
            || lvlBox.getSelectedItem() == JobLevel.Default
            || salaryField.getText().isBlank()
            || phoneField.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Please fill out all the fields");
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
        var e = new Employee();
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

    //For update button
    public static Employee existingEmployeeFromDB(String eid, JTextField nameField, JComboBox<Education> eduBox,
    JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox, JComboBox<Gender> genderBox,
    JTextField phoneField, JTextField emailField, JTextField salaryField)
    {
        Employee e = newEmployeeToDB(nameField, eduBox, deptBox, lvlBox, genderBox, phoneField, emailField, salaryField);
        e.setEID(eid);
        return e;
    }

    public static void resetInput(JTextField nameField, JComboBox<Education> eduBox, 
    JComboBox<Department> deptBox, JComboBox<JobLevel> lvlBox, JComboBox<Gender> genderBox,
    JTextField phoneField, JTextField emailField,JTextField salaryField) 
    {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        salaryField.setText("");
        if (genderBox.getItemCount() > 0) genderBox.setSelectedIndex(0);
        if (deptBox.getItemCount() > 0) deptBox.setSelectedIndex(0);
        if (lvlBox.getItemCount()  > 0) lvlBox.setSelectedIndex(0);
        if (eduBox.getItemCount()  > 0) eduBox.setSelectedIndex(0);

    }

    public static void exportToCSV(JTable table, File file) throws IOException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8"))) {

            //Header/Column Title
            for (int c = 0; c < model.getColumnCount(); c++) {
                pw.print(model.getColumnName(c));
                if (c < model.getColumnCount() - 1) pw.print(",");
            }
            pw.println();

            //Data/Row
            for (int r = 0; r < model.getRowCount(); r++) {
                for (int c = 0; c < model.getColumnCount(); c++) {
                    Object value = model.getValueAt(r, c);
                    pw.print(value == null ? "" : value.toString());
                    if (c < model.getColumnCount() - 1) pw.print(",");
                }
                pw.println();
            }
        }
    }

    public static void exportToXlsx(JTable table, File file) throws IOException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Workbook wb = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet("Employees");

        // Header
        Row header = sheet.createRow(0);
        for (int c = 0; c < model.getColumnCount(); c++) {
            Cell cell = header.createCell(c);
            cell.setCellValue(model.getColumnName(c));
        }

        // Data
        for (int r = 0; r < model.getRowCount(); r++) {
            Row row = sheet.createRow(r + 1);
            for (int c = 0; c < model.getColumnCount(); c++) {
                Object value = model.getValueAt(r, c);
                Cell cell = row.createCell(c);
                if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue());
                } else {
                    cell.setCellValue(value == null ? "" : value.toString());
                }
            }
        }

        try (FileOutputStream out = new FileOutputStream(file)) {
            wb.write(out);
        }
        wb.close();
    }
}

