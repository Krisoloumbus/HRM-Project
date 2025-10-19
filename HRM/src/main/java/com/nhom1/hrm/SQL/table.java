package com.nhom1.hrm.SQL;
import java.sql.Connection;
import java.sql.Statement;

public class table {
        private static final String taobang ="""
        IF NOT EXISTS (
            SELECT 1
            FROM sys.tables t
            JOIN sys.schemas s ON s.schema_id = t.schema_id
            WHERE t.name = 'Employees' AND s.name = 'dbo'
        )
        BEGIN
            CREATE TABLE dbo.Employees(
                No INT IDENTITY(1,1) CONSTRAINT PK_Employees PRIMARY KEY,
                EID AS ('HRM' + RIGHT('00000' + CAST(No AS VARCHAR(5)), 5)) PERSISTED UNIQUE,
                Full_Name NVARCHAR(100) NOT NULL,
                Phone NUMERIC(10,0) NOT NULL,
                Email VARCHAR(255) UNIQUE,
                Education VARCHAR(50) NOT NULL,
                Department VARCHAR(50) NOT NULL,
                Level VARCHAR(50) NOT NULL,
                Salary MONEY NOT NULL,
                CreatedAt DATETIME2 DEFAULT SYSUTCDATETIME()
            );
        END
                """;
        public static void taobangifchuaco (Connection conn)
        throws Exception {
            try (Statement st = conn.createStatement()){
                st.execute(taobang);
            }
        }
    }
