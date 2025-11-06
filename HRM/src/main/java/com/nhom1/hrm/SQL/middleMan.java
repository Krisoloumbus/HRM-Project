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
import com.nhom1.hrm.models.Gender;
import com.nhom1.hrm.models.JobLevel;

public class middleMan extends Employee {
    public List<Employee> findAll(Connection c) throws SQLException {
        String sql = """
            SELECT No, EID, Full_Name, Gender, Phone, Email, Education, Department, Job_Level, Salary
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
                e.setEdu(Education.fromCodeToDB(rs.getString("Education")));
                e.setDepartment(Department.fromCodeToDB(rs.getString("Department")));
                e.setLevel(JobLevel.fromCodeToDB(rs.getString("Job_Level")));
                e.setGender(Gender.fromCodeToDB(rs.getString("Gender")));
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
            (Full_Name, Gender, Phone, Email, Education, Department, Job_Level, Salary)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getGender().getCodeDB());
            ps.setString(3, e.getPhone());
            ps.setString(4, e.getEmail());
            ps.setString(5, e.getEdu().getCodeDB());
            ps.setString(6, e.getDepartment().getCodeDB());
            ps.setString(7, e.getLevel().getCodeDB());
            ps.setBigDecimal(8, e.getSalary() != null ? e.getSalary() : BigDecimal.ZERO);
            return ps.executeUpdate();
        }
    }

    //Delete by selected row(EID)
    public int delByEID(Connection c, String eid) throws SQLException{
        String sql = "DELETE FROM dbo.Employees WHERE EID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql))
        {
                ps.setString(1, eid);
                return ps.executeUpdate();
        }
    }

    //Update selected - testing
    public int update(Connection c, Employee e) throws SQLException {
        String sql = """
            UPDATE dbo.Employees
            SET Full_Name = ?, Gender = ?, Phone = ?, Email = ?,
            Education = ?, Department = ?, Job_Level = ?, Salary = ?
            WHERE EID = ?
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getGender().getCodeDB());
            ps.setString(3, e.getPhone());
            ps.setString(4, e.getEmail());
            ps.setString(5, e.getEdu().getCodeDB());
            ps.setString(6, e.getDepartment().getCodeDB());
            ps.setString(7, e.getLevel().getCodeDB());
            ps.setBigDecimal(8, e.getSalary() != null ? e.getSalary() : BigDecimal.ZERO);
            ps.setString(9, e.getEID()); //
            return ps.executeUpdate();
        }
    }
}