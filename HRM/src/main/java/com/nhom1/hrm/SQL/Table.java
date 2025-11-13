package com.nhom1.hrm.SQL;
import java.sql.Connection;
import java.sql.Statement;

public class Table {
        private static final String empCreatTable ="""
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
                Full_Name NVARCHAR(255) NOT NULL,
                Gender VARCHAR(10) NOT NULL,
                Phone NUMERIC(10,0) NOT NULL,
                Email VARCHAR(255) NULL,
                Education VARCHAR(255) NOT NULL,
                Department VARCHAR(255) NOT NULL,
                Job_Level VARCHAR(255) NOT NULL,
                Salary DECIMAL(19,4) NOT NULL
            );
        END
                """;
        
        private static final String userCreatTable = """
            IF NOT EXISTS (
            SELECT 1
            FROM sys.tables t
            JOIN sys.schemas s ON s.schema_id = t.schema_id
            WHERE t.name = 'Users' AND s.name = 'dbo'
        )
        BEGIN
            CREATE TABLE dbo.Users(
                UID           INT IDENTITY(1,1) PRIMARY KEY,
                Username      NVARCHAR(64)  NOT NULL UNIQUE,
                PasswordHash  VARBINARY(32) NOT NULL,   -- SHA-256 = 32 bytes
                Salt          VARBINARY(16) NOT NULL,   -- 128-bit salt
                CreatedAt     DATETIME2(0)  NOT NULL DEFAULT SYSUTCDATETIME()
            );
            CREATE UNIQUE INDEX IX_Users_Username ON dbo.Users(Username);
        END
                """;

        public static void createEmpIfNotHave (Connection conn)
        throws Exception {
            try (Statement st = conn.createStatement()){
                st.execute(empCreatTable);
            }
        }

        public static void createUserIfNotHave (Connection conn)
        throws Exception {
            try (Statement st = conn.createStatement()){
                st.execute(userCreatTable);
            }
        }

        
    }
