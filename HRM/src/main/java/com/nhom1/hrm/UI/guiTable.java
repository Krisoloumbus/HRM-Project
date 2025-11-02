package com.nhom1.hrm.UI;

import java.sql.Connection;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.nhom1.hrm.SQL.connectSQL;
import com.nhom1.hrm.SQL.middleMan;
import com.nhom1.hrm.SQL.table;

public final class guiTable{
    private guiTable(){}
    public static void loadTable(JTable eTable)throws Exception{
        DefaultTableModel eModel = new DefaultTableModel(
            new Object[]{"No","EID","Full Name","Gender","Education","Phone","Email","Department","Level","Salary"},0
        ) {
            @Override public boolean isCellEditable(int rows, int collumn){return false;}
        };
        try (Connection con = connectSQL.getConnection()){
            table.taobangifchuaco(con);
            var eMiddleman = new middleMan();
            for (var e : eMiddleman.findAll(con)){
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
