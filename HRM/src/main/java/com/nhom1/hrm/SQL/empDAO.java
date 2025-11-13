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

public class empDAO extends Employee {
    //This is not Searching
    //Just use to get selected data from SQL_DB to show it in GUI table
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

    //Insert
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

    //Updating selected row
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

    //This is the real Searching :)))))
    public List<Employee> searchEmployees(Connection c, String name, Gender gender,
    Education edu, JobLevel lvl, Department dept, String phone, String email) throws  SQLException {
        StringBuilder sbSQL = new StringBuilder("SELECT EID, Full_Name, Gender, Education, Phone, Email, " + 
        "Department, Job_Level, Salary " + "FROM dbo.Employees WHERE 1=1");
            ArrayList<Object> params = new ArrayList<>();
            if(name != null && name.isBlank()){
                sbSQL.append("AND Full_Name LIKE ?");
                params.add("%" + name + "%");
            }

            if (gender != Gender.Default){
                sbSQL.append("AND Gender = ?");
                params.add(gender.getCodeDB());
            }

            if (edu != Education.Default){
                sbSQL.append("AND Education = ?");
                params.add(edu.getCodeDB());
            }

            if (lvl != JobLevel.Default) {
                sbSQL.append("AND Job_Level = ?");
                params.add(lvl.getCodeDB());
            }

            if (dept != Department.Default) {
                sbSQL.append("AND Department = ?");
                params.add(dept.getCodeDB());
            }

            if(phone != null && phone.isBlank()){
                sbSQL.append("AND Phone LIKE ?");
                params.add("%" + phone + "%");
            }

            if(email != null && email.isBlank()){
                sbSQL.append("AND Email LIKE ?");
                params.add("%" + email + "%");
            }
            try (PreparedStatement ps = c.prepareStatement(sbSQL.toString())){
                for (int i = 0; i < params.size(); i ++) {
                    ps.setObject(i + 1, params.get(i));
                }
                try (ResultSet rs = ps.executeQuery()) {
                List<Employee> list = new ArrayList<>();
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEID(rs.getString("EID"));
                    e.setName(rs.getString("Full_Name"));
                    e.setGender(Gender.fromCodeToDB(rs.getString("Gender")));
                    e.setEdu(Education.fromCodeToDB(rs.getString("Education")));
                    e.setPhone(rs.getString("Phone"));
                    e.setEmail(rs.getString("Email"));
                    e.setDepartment(Department.fromCodeToDB(rs.getString("Department")));
                    e.setLevel(JobLevel.fromCodeToDB(rs.getString("Job_Level")));
                    e.setSalary(rs.getBigDecimal("Salary"));
                    list.add(e);
                }
                return list;
            }
        }
    }
}