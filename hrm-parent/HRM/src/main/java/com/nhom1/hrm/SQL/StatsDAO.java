/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 *
 * @author Kris
 */
public class StatsDAO {
     public enum Field {
        GENDER("Gender"),
        DEPARTMENT("Department"),
        EDUCATION("Education"),
        JOBLEVEL("Job_Level");   // đúng tên cột của bạn

        public final String col;
        Field(String col) { this.col = col; }
    }

    /** Đếm theo cột cố định (an toàn, không nhận tên cột tự do) */
    public static LinkedHashMap<String, Integer> countBy(Connection c, Field f) throws SQLException {
        String col = f.col;
        String sql = "SELECT " + col + " AS k, COUNT(*) AS total " +
                     "FROM dbo.Employees GROUP BY " + col + " ORDER BY k";
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String key = rs.getString("k");   // có thể null -> hiển thị "N/A"
                if (key == null) key = "N/A";
                map.put(key, rs.getInt("total"));
            }
        }
        return map;
    }

    // Các hàm tiện dụng
    public static LinkedHashMap<String,Integer> countByGender(Connection c)     throws SQLException { return countBy(c, Field.GENDER); }
    public static LinkedHashMap<String,Integer> countByDepartment(Connection c) throws SQLException { return countBy(c, Field.DEPARTMENT); }
    public static LinkedHashMap<String,Integer> countByEducation(Connection c)  throws SQLException { return countBy(c, Field.EDUCATION); }
    public static LinkedHashMap<String,Integer> countByJobLevel(Connection c)   throws SQLException { return countBy(c, Field.JOBLEVEL); }
}
