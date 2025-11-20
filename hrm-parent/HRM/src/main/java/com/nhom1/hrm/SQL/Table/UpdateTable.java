package com.nhom1.hrm.SQL.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTable {
    private UpdateTable(){}

        // ===== Helpers =====

    private static void exec(Connection con, String sql) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        }
    }

    /** Escape dấu nháy đơn để nhúng vào N'...'. (Chỉ dùng cho DDL ở đây) */
    private static String escapeSql(String s) {
        return s == null ? "" : s.replace("'", "''");
    }
    
    public static void migrateB(Connection con) throws SQLException {
        migrateB(con, "");
    }

    /** Migrate B với backfill tuỳ chọn (ví dụ: "Chưa cập nhật") */
    public static void migrateB(Connection con, String backfillValue) throws SQLException {
        // 1) Thêm cột nếu chưa có (NULL)
        final String step1 = """
                             IF COL_LENGTH('dbo.Employees','Address') IS NULL 
                             BEGIN 
                                 ALTER TABLE dbo.Employees ADD Address NVARCHAR(200) NULL; 
                             END""";
        exec(con, step1);

        // 2) Backfill mọi dòng NULL
        final String step2 = """
                             IF COL_LENGTH('dbo.Employees','Address') IS NOT NULL 
                             BEGIN 
                                 UPDATE dbo.Employees 
                                 SET Address = N'""" + escapeSql(backfillValue) + "' \n"
            + "    WHERE Address IS NULL; \n"
            + "END";
        exec(con, step2);

        // 3) Nếu cột còn đang cho phép NULL → đổi sang NOT NULL
        final String step3 = """
                             IF EXISTS (SELECT 1 
                                        FROM INFORMATION_SCHEMA.COLUMNS 
                                        WHERE TABLE_SCHEMA = 'dbo' 
                                          AND TABLE_NAME  = 'Employees' 
                                          AND COLUMN_NAME = 'Address' 
                                          AND IS_NULLABLE = 'YES') 
                             BEGIN 
                                 ALTER TABLE dbo.Employees 
                                 ALTER COLUMN Address NVARCHAR(200) NOT NULL; 
                             END""";
        exec(con, step3);
    }
}
