package com.nhom1.hrm.UI.Utils;

import com.nhom1.hrm.UI.Utils.Function;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.nhom1.hrm.Models.Department;
import com.nhom1.hrm.Models.Education;
import com.nhom1.hrm.Models.Gender;
import com.nhom1.hrm.Models.JobLevel;


public class MenuBar {

    public static void aboutSwitchCardOnClick(JMenuItem aboutItem, Cards deck, String cardName) {
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

    public static void onExport(Component parent, JTable eTable) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter("Excel File (*.xlsx)", "xlsx");
        FileNameExtensionFilter csvFilter =new FileNameExtensionFilter("CSV File (*.csv)", "csv");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(xlsxFilter);
        chooser.addChoosableFileFilter(csvFilter);
        chooser.setFileFilter(xlsxFilter); 
        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (chooser.getFileFilter() == csvFilter && !file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getParentFile(), file.getName() + ".csv");
                try {
                    Function.exportToCSV(eTable, file);
                    JOptionPane.showMessageDialog(parent, "" + file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(parent, "Export failed: " + e.getMessage());
                }
            } else if (chooser.getFileFilter() == xlsxFilter && !file.getName().toLowerCase().endsWith(".xlsx")){
                file = new File(file.getParentFile(), file.getName() + ".xlsx");
                try {
                    Function.exportToXlsx(eTable, file);
                    JOptionPane.showMessageDialog(parent, "" + file.getAbsolutePath());  
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(parent, "Export failed: " + e.getMessage());
                }
            }
        }
    }

    public static void onLogoutAndRelaunch(JFrame appFrame,
                                           AuthProvider auth,
                                           Supplier<? extends JFrame> appFactory,
                                           boolean confirm) {
        // 1) Confirm (nếu cần)
        if (confirm) {
            int c = JOptionPane.showConfirmDialog(
                    appFrame,
                    "Are you sure you want to log out?",
                    "Confirm log out",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (c != JOptionPane.YES_OPTION) return;
        }

        // 2) Đóng AppShell hiện tại
        if (appFrame != null) {
            appFrame.dispose();
        }

        // 3) Lấy AuthProvider (nếu bạn không truyền vào thì thử lấy từ clientProperty)
        if (auth == null && appFrame != null) {
            Object v = appFrame.getRootPane().getClientProperty("auth");
            if (v instanceof AuthProvider) auth = (AuthProvider) v;
        }

        // 4) Hiện lại màn hình đăng nhập
        boolean ok = LoginController.showDialog(null, auth);
        if (!ok) {
            System.exit(0); // người dùng Cancel ở login -> thoát hẳn
            return;
        }

        // 5) Đăng nhập thành công -> tạo lại AppShell và hiển thị
        JFrame newApp = (appFactory != null) ? appFactory.get() : null;
        if (newApp == null) {
            JOptionPane.showMessageDialog(null, "Cannot relaunch app: appFactory is null");
            System.exit(0);
            return;
        }

        // truyền lại auth cho vòng đời mới (nếu có)
        if (auth != null) {
            newApp.getRootPane().putClientProperty("auth", auth);
        }

        newApp.setLocationRelativeTo(null);
        newApp.setVisible(true);
    }
}