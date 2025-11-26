/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nhom1.updater;

import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Kris
 */
public class Updater {

    public static void main(String[] args) throws AtomicMoveNotSupportedException {
        if (args.length < 3) {
            System.err.println("Usage: java -jar updater.jar <targetJar> <newJar> <relaunchCmd>");
            System.exit(2);
        }
        Path target   = Paths.get(args[0]).toAbsolutePath();  // hrm-app.jar hiện tại
        Path newJar   = Paths.get(args[1]).toAbsolutePath();  // file mới đã tải
        String relaunchCmd = args[2];                          // lệnh khởi động lại

        Path appDir = target.getParent();
        System.out.println("[updater] target = " + target);
        System.out.println("[updater] newJar = " + newJar);
        System.out.println("[updater] relaunch = " + relaunchCmd);

        // 1) Chờ app thoát hoàn toàn (Windows đôi khi giữ lock 1–2s)
        sleepQuiet(600);
        // Thử nhiều lần nếu vẫn đang bị khóa
        for (int i = 0; i < 10; i++) {
            try {
                try {
                    // Thử move bình thường (không ATOMIC_MOVE để hỗ trợ khác ổ đĩa)
                    Files.move(newJar, target, StandardCopyOption.REPLACE_EXISTING);
                } catch (FileSystemException ex) {
                    // Fallback: copy rồi replace
                    //java.nio.file.AtomicMoveNotSupportedException | java.nio.file.FileSystemException 
                    Path bak = target.resolveSibling(target.getFileName() + ".bak");
                    try { Files.deleteIfExists(bak); } catch (Exception ignore) {}
                    Files.copy(newJar, target, StandardCopyOption.REPLACE_EXISTING);
                    Files.deleteIfExists(newJar);
                }
                break; // OK
                } catch (Exception ex) {
                    sleepQuiet(500);
                    if (i == 9) { ex.printStackTrace(); show("Update failed: " + ex.getMessage()); System.exit(1); }
                }
            }

        // 2) Relaunch app
        try {
            ProcessBuilder pb = buildRelaunch(relaunchCmd);
            pb.directory(appDir.toFile());
            pb.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            show("Updated but cannot relaunch: " + ex.getMessage());
            System.exit(1);
        }
    }

    // ===== helpers =====
    static void sleepQuiet(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    static void show(String msg) {
        try {
            // Tránh phụ thuộc Swing; in console là đủ, nhưng vẫn popup nếu có
            Class<?> jp = Class.forName("javax.swing.JOptionPane");
            jp.getMethod("showMessageDialog", java.awt.Component.class, Object.class)
              .invoke(null, null, msg);
        } catch (Throwable t) {
            System.out.println("[updater] " + msg);
        }
    }

    /** Trên Windows dùng cmd /c "..." để không phải tự tách chuỗi; Linux/Mac dùng sh -c */
    static ProcessBuilder buildRelaunch(String cmdline) {
        boolean win = System.getProperty("os.name").toLowerCase().contains("win");
        if (win) {
            return new ProcessBuilder("cmd", "/c", cmdline);
        } else {
            return new ProcessBuilder("sh", "-c", cmdline);
        }
    }
}
