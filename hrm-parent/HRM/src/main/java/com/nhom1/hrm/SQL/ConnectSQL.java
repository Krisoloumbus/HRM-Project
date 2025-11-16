package com.nhom1.hrm.SQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectSQL {
    
    /*public static final Connection getConnection() throws SQLException {
        //Dotenv env = Dotenv.configure().directory("hrm-parent\\release\\db.env").filename("db.env").load();
        //Dotenv env = Dotenv.configure().directory("./db.env").filename("db.env").load();
        Dotenv env = Dotenv.configure().directory("src/main/java/com/nhom1/hrm/SQL").filename("db.env").load();
        Dotenv env = Dotenv.configure().directory("./db.env").load();
        String dbURL = env.get("dbURL");
        String dbName = env.get("dbName");
        String dbUserName = env.get("dbUser");
        String dbPassword = env.get("dbPass");
        String connectionURL = dbURL + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true";
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(connectionURL, dbUserName, dbPassword);
    }*/

    public static Connection getConnection() throws SQLException {
    // 1) VM option hoặc file cạnh JAR (Dotenv đọc từ filesystem)
    Dotenv dotenv = tryLoadFromSystemOrJarDir();

    String dbURL, dbName, dbUser, dbPass;

    if (dotenv != null && notBlank(dotenv.get("dbURL"))) {
        dbURL  = trim(dotenv.get("dbURL"));
        dbName = trim(dotenv.get("dbName"));
        dbUser = trim(dotenv.get("dbUser"));
        dbPass = trim(dotenv.get("dbPass"));
    } else {
        // 2) Fallback: đọc từ classpath (resource bên trong JAR)
        Map<String,String> env = loadFromClasspath("/db.env"); // đặt file ở src/main/resources
        dbURL  = env.get("dbURL");
        dbName = env.get("dbName");
        dbUser = env.get("dbUser");
        dbPass = env.get("dbPass");
    }

    if (notBlank(dbURL) && notBlank(dbName) && notBlank(dbUser) && dbPass != null) {
        String connectionURL = dbURL + ";databaseName=" + dbName
                + ";encrypt=true;trustServerCertificate=true;loginTimeout=5";
        return DriverManager.getConnection(connectionURL, dbUser, dbPass);
    }
    throw new IllegalStateException(
        "Missing DB config. Check db.env keys: dbURL, dbName, dbUser, dbPass.");
}

private static Dotenv tryLoadFromSystemOrJarDir() {
    try {
        // 1) -Dhrm.env.dir=...
        String dir = System.getProperty("hrm.env.dir");
        if (notBlank(dir)) {
            Dotenv d = Dotenv.configure()
                    .directory(dir)
                    .filename("db.env")
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();
            if (notBlank(d.get("dbURL"))) return d;
        }

        // 2) thư mục chứa JAR/target/classes hiện tại
        Path loc = null;
        try {
            loc = Paths.get(ConnectSQL.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
        } catch (Exception ignore) { /* no-op */ }

        Path jarDir = (loc != null && Files.isDirectory(loc)) ? loc : (loc != null ? loc.getParent() : null);
        if (jarDir != null) {
            Dotenv d = Dotenv.configure()
                    .directory(jarDir.toString())
                    .filename("db.env")
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();
            if (notBlank(d.get("dbURL"))) return d;
        }
    } catch (Exception ignore) { /* keep fallback */ }

    return null; // để fallback sang classpath
}

private static Map<String,String> loadFromClasspath(String resourcePath) {
    Map<String,String> map = new HashMap<>();
    try (InputStream is = ConnectSQL.class.getResourceAsStream(resourcePath)) {
        if (is == null) {
            throw new IllegalStateException("Classpath resource not found: " + resourcePath);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                int eq = line.indexOf('=');
                if (eq <= 0) continue;
                String k = line.substring(0, eq).trim();
                String v = line.substring(eq + 1).trim();
                map.put(k, v);
            }
        }
    } catch (IOException e) {
        throw new IllegalStateException("Failed to read " + resourcePath + ": " + e.getMessage(), e);
    }
    return map;
}

private static boolean notBlank(String s) {
    return s != null && !s.isBlank();
}
private static String trim(String s) {
    return s == null ? null : s.trim();
}

}