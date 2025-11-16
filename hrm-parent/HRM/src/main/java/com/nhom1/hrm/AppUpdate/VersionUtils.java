/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.AppUpdate;

import javax.swing.JOptionPane;

/**
 *
 * @author Kris
 */
public class VersionUtils {
    private VersionUtils() {}

    public static String currentVersion() {
        String v = VersionUtils.class.getPackage().getImplementationVersion();
        return (v != null && !v.isBlank()) ? v : "0.0.0";
    }

    public static int compareSemver(String a, String b) {
        String[] A = a.split("\\.");
        String[] B = b.split("\\.");
        for (int i=0;i<3;i++) {
            int ai = i<A.length ? Integer.parseInt(A[i]) : 0;
            int bi = i<B.length ? Integer.parseInt(B[i]) : 0;
            if (ai != bi) return Integer.compare(ai, bi);
        }
        return 0;
    }

    public static void getVersion() {
        JOptionPane.showMessageDialog(null,"Version: " + currentVersion());
    }
}
