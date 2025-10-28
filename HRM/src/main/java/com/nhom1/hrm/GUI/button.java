package com.nhom1.hrm.GUI;

import java.sql.Connection;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public final class button extends gui{
    private button(){}
    public static void onAdd(JTextField nameField, JComboBox eduBox, JComboBox deptBox, JComboBox lvlBox,
                             JTextField phoneField, JTextField emailField, JTextField salaryField,
                             JTable eTable) {
        if (!validate.validateForm(nameField, eduBox, deptBox, lvlBox, emailField, phoneField, salaryField)) return;

        var emp = validate.read(nameField, eduBox, deptBox, lvlBox, phoneField, emailField, salaryField);
        try (Connection c = com.nhom1.hrm.SQL.connectSQL.getConnection()) {
            com.nhom1.hrm.SQL.table.taobangifchuaco(c);
            new com.nhom1.hrm.SQL.middleMan().insert(c, emp);
            JOptionPane.showMessageDialog(null, "Đã thêm nhân viên!");
            tableAction.loadTable(eTable);
            validate.clear(nameField, eduBox, deptBox, lvlBox, phoneField, emailField, salaryField);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi thêm: " + ex.getMessage());
        }
    }
public static void onDelete(JTable eTable) {
        int[] sel = eTable.getSelectedRows();
        if (sel == null || sel.length == 0) {
            JOptionPane.showMessageDialog(null, "Hãy chọn ít nhất một dòng để xóa.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(null, "Xóa " + sel.length + " bản ghi?", "Xác nhận",
                                               JOptionPane.YES_NO_OPTION);
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
            tableAction.loadTable(eTable);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi xóa: " + ex.getMessage());
        }
    }
}
