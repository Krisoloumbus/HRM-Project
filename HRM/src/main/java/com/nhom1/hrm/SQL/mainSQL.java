package com.nhom1.hrm.SQL;

import java.sql.Connection;

public class mainSQL{
    public static void main(String[] args) {
        try {
            Connection conn = connectSQL.getConnection();
            System.out.println("Connected");
            try {
                table.taobangifchuaco(conn);
                System.out.println("Table created");
            } catch (Exception e1) {
                System.out.println("Failed to create table");
                e1.printStackTrace();}
        } catch (Exception e2) {
            System.out.println("Connect failed");
            e2.printStackTrace();
        }
    }
}
