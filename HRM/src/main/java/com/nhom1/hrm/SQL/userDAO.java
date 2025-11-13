/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.SQL;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author Kris
 */
public class userDAO {
    /** Tạo user mới. Trả về true nếu tạo được, false nếu username đã tồn tại. */
    public static boolean createUser(Connection c, String username, char[] password) throws Exception {
        byte[] salt = randomSalt(16);
        byte[] hash = hashPassword(password, salt);

        String sql = "INSERT INTO dbo.Users (Username, PasswordHash, Salt) VALUES (?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setNString(1, username.trim());
            ps.setBytes(2, hash);
            ps.setBytes(3, salt);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            // 2627 = unique constraint violation
            if (ex.getErrorCode() == 2627) return false;
            throw ex;
        } finally {
            // xoá dấu vết trong RAM
            zero(password);
            zero(hash);
            zero(salt);
        }
    }

    /** Kiểm tra đăng nhập: true nếu user/pass đúng. */
    public static boolean check(Connection c, String username, char[] password) throws Exception {
        String sql = "SELECT PasswordHash, Salt FROM dbo.Users WHERE Username = ?";
        byte[] dbHash = null, salt = null;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setNString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false; // không có user
                dbHash = rs.getBytes("PasswordHash");
                salt   = rs.getBytes("Salt");
            }
        }

        byte[] candidate = null;
        try {
            candidate = hashPassword(password, salt);
            // so sánh constant-time
            return MessageDigest.isEqual(dbHash, candidate);
        } finally {
            zero(password);
            zero(candidate);
            zero(salt);
            zero(dbHash);
        }
    }

    /** Đổi mật khẩu (nếu cần) */
    public static boolean updatePassword(Connection c, String username, char[] newPassword) throws Exception {
        byte[] salt = randomSalt(16);
        byte[] hash = hashPassword(newPassword, salt);

        String sql = "UPDATE dbo.Users SET PasswordHash = ?, Salt = ? WHERE Username = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBytes(1, hash);
            ps.setBytes(2, salt);
            ps.setNString(3, username.trim());
            return ps.executeUpdate() == 1;
        } finally {
            zero(newPassword);
            zero(salt);
            zero(hash);
        }
    }

    // === Helpers ===

    private static byte[] hashPassword(char[] password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // chuyển tạm sang bytes UTF-8
        byte[] passBytes = new String(password).getBytes(StandardCharsets.UTF_8);
        try {
            md.update(salt);
            return md.digest(passBytes);
        } finally {
            zero(passBytes);
        }
    }

    private static byte[] randomSalt(int n) {
        byte[] salt = new byte[n];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private static void zero(byte[] arr) {
        if (arr != null) Arrays.fill(arr, (byte) 0);
    }
    private static void zero(char[] arr) {
        if (arr != null) Arrays.fill(arr, '\0');
    }
}
