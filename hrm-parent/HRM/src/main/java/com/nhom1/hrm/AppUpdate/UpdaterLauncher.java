/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.AppUpdate;

import java.awt.Component;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

/**
 *
 * @author Kris
 */
public class UpdaterLauncher {
    private UpdaterLauncher() {}

    public static void runUpdateAndRelaunch(Component parent) {
        try {
            // Phiên bản hiện tại từ Manifest
            String cur = VersionUtils.currentVersion();

            // Release mới nhất
            var rel = GithubReleaseClient.fetchLatestRelease();
            String latest = GithubReleaseClient.latestVersion(rel);

            if (VersionUtils.compareSemver(latest, cur) <= 0) {
                JOptionPane.showMessageDialog(parent, "You're already on the latest version: " + cur);
                return;
            }
            int c = JOptionPane.showConfirmDialog(parent,
                    "New version " + latest + " is available (current: " + cur + "). Update now?",
                    "Update available", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (c != JOptionPane.YES_OPTION) return;

            // URL asset .jar
            var urlOpt = GithubReleaseClient.assetUrl(rel, GithubReleaseClient.ASSET_NAME);
            if (urlOpt.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "No asset " + GithubReleaseClient.ASSET_NAME + " in release!");
                return;
            }

            // Tải về thư mục tạm
            Path tmp = Files.createTempDirectory("hrm-update-");
            Path newJar = tmp.resolve(GithubReleaseClient.ASSET_NAME);
            GithubReleaseClient.download(urlOpt.get(), newJar);

            // Đường dẫn jar đang chạy & updater.jar
            Path runningJar = Paths.get(UpdaterLauncher.class
                    .getProtectionDomain().getCodeSource().getLocation().toURI());
            Path appDir = runningJar.getParent();
            Path updaterJar = appDir.resolve("updater.jar");
            if (!Files.exists(updaterJar)) {
                JOptionPane.showMessageDialog(parent, "updater.jar not found next to app jar!");
                return;
            }

            // Lệnh relaunch: nếu chạy bằng jpackage, dùng EXE; ngược lại java -jar
            String jpkg = System.getProperty("jpackage.app-path");
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + (System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java");

            String relaunchCmd = (jpkg != null && !jpkg.isBlank())
                    ? "\"" + jpkg + "\""                            // ví dụ: "C:\Program Files\HRM\HRM.exe"
                    : "\"" + javaBin + "\" -jar \"" + runningJar.toAbsolutePath() + "\"";

            // Gọi updater rồi thoát ứng dụng
            new ProcessBuilder(javaBin, "-jar",
                    updaterJar.toString(),
                    runningJar.toAbsolutePath().toString(),
                    newJar.toAbsolutePath().toString(),
                    relaunchCmd
            ).inheritIO().start();

            System.exit(0);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Update failed: " + ex.getMessage());
        }
    }
}
