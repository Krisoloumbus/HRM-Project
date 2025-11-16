package com.nhom1.hrm.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectSQL {
    public static final Connection getConnection() throws SQLException {
        String dir = System.getProperty("hrm.env.dir", ".");
        Dotenv env = Dotenv.configure().directory(dir).filename("db.env").ignoreIfMalformed().ignoreIfMissing().load();
        String dbURL = env.get("dbURL");
        String dbName = env.get("dbName");
        String dbUserName = env.get("dbUser");
        String dbPassword = env.get("dbPass");
        String connectionURL = dbURL + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true";
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(connectionURL, dbUserName, dbPassword);
    }
}