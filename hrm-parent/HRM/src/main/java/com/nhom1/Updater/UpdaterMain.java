/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.Updater;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Set;

/**
 *
 * @author Kris
 */
public class UpdaterMain {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: java -jar updater.jar <runningJar> <newJar> <relaunchCmd>");
            System.exit(2);
        }
        Path runningJar = Paths.get(args[0]);
        Path newJar     = Paths.get(args[1]);
        String relaunch = args[2];

        waitForUnlock(runningJar, 60_000);

        Path backup = runningJar.resolveSibling(runningJar.getFileName() + ".bak");
        try { Files.deleteIfExists(backup); } catch (Exception ignore){}

        try { Files.move(runningJar, backup, StandardCopyOption.REPLACE_EXISTING); } catch (Exception ignore){}
        Files.copy(newJar, runningJar, StandardCopyOption.REPLACE_EXISTING);

        ProcessBuilder pb = isWindows()
                ? new ProcessBuilder("cmd", "/c", relaunch)
                : new ProcessBuilder("sh", "-c", relaunch);
        pb.inheritIO().start();
    }

    private static boolean isWindows() {
        return System.getProperty("os.name","").toLowerCase().contains("win");
    }

    private static void waitForUnlock(Path file, long timeoutMs) throws InterruptedException {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeoutMs) {
            try (var ch = FileSystems.getDefault().provider()
                    .newByteChannel(file, Set.of(StandardOpenOption.WRITE))) {
                return; // mở ghi được → không bị lock nữa
            } catch (IOException e) {
                Thread.sleep(300);
            }
        }
    }
}
