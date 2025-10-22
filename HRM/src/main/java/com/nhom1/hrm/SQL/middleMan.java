package com.nhom1.hrm.SQL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nhom1.hrm.models.Department;
import com.nhom1.hrm.models.Education;
import com.nhom1.hrm.models.Employee;
import com.nhom1.hrm.models.Level;

public class middleMan extends Employee {
 public List<Employee> findAll(Connection c) throws SQLException {
        String sql = """
            SELECT No, EID, Full_Name, Phone, Email, Education, Department, Job_Level, Salary
            FROM dbo.Employees ORDER BY No
        """;
        List<Employee> out = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employee e = new Employee();
                e.setNo(rs.getInt("No"));
                e.setEID(rs.getString("EID"));
                e.setName(rs.getString("Full_Name"));
                e.setPhone(rs.getString("Phone"));
                e.setEmail(rs.getString("Email"));
                //e.setEdu(rs.getString("Education"));
                e.setEdu(Education.fromCodeToDB(rs.getString("Education")));
                //e.setDepartment(rs.getString("Department"));
                e.setDepartment(Department.fromCodeToDB(rs.getString("Department")));
                //e.setLevel(rs.getString("Job_Level"));
                e.setLevel(Level.fromCodeToDB(rs.getString("Job_Level")));
                e.setSalary(rs.getBigDecimal("Salary"));
                out.add(e);
            }
        }
        return out;
    }

    // Insert: KHÔNG truyền EID vì là computed column
    public int insert(Connection c, Employee e) throws SQLException {
        String sql = """
            INSERT INTO dbo.Employees
            (Full_Name, Phone, Email, Education, Department, Job_Level, Salary)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getPhone());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getEdu().getCodeDB());
            ps.setString(5, e.getDepartment().getCodeDB());
            ps.setString(6, e.getLevel().getCodeDB());
            ps.setBigDecimal(7, e.getSalary() != null ? e.getSalary() : BigDecimal.ZERO);
            return ps.executeUpdate();
        }
    }
}
