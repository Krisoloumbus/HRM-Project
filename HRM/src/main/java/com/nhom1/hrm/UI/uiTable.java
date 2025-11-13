package com.nhom1.hrm.UI;

import java.sql.Connection;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.nhom1.hrm.SQL.connectSQL;
import com.nhom1.hrm.SQL.empDAO;
import com.nhom1.hrm.SQL.table;

public final class uiTable{
    private uiTable(){}
    public static void loadTable(JTable eTable)throws Exception{
        DefaultTableModel eModel = new DefaultTableModel(
            new Object[]{"No","EID","Full Name","Gender","Education","Phone","Email","Department","Level","Salary"},0
        ) {
            @Override public boolean isCellEditable(int rows, int collumn){return false;}
        };
        try (Connection c = connectSQL.getConnection()){
            table.createEmpIfNotHave(c);
            var mm = new empDAO();
            for (var e : mm.findAll(c)){
                eModel.addRow(new Object[]{e.getNo(), e.getEID(), e.getName(), e.getGender(), e.getEdu(), e.getPhone(),
                e.getEmail(), e.getDepartment(), e.getLevel(), e.getSalary()});
            }
            eTable.setModel(eModel);
            eTable.setAutoCreateRowSorter(true);
            eTable.setFillsViewportHeight(true);
            //eTable.setAutoCreateColumnsFromModel(true); This is kinda useless for this app version
        } catch (Exception ex) {
        ex.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(eTable,"Loading table error:" + ex.getMessage());
        }
    }
}
