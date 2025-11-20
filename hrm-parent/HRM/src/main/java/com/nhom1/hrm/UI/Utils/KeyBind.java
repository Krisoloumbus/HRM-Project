/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI.Utils;

import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kris
 */
public class KeyBind {

    //ESC to close dialog
    // Dành cho mọi "cửa sổ" có root pane: JFrame, JDialog, JWindow, ...
    public static void bindEscToClose(RootPaneContainer c) {
        final JRootPane root = c.getRootPane();
        root.registerKeyboardAction(e -> { 
                // Tìm Window chứa root pane (có dispose()) theo cách an toàn
                Window win = SwingUtilities.getWindowAncestor(root);
                if (win == null) return;
                int opt = JOptionPane.showConfirmDialog(win, "You want to closing?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opt == JOptionPane.YES_OPTION) {
                    win.dispatchEvent(new WindowEvent(win, WindowEvent.WINDOW_CLOSING));
                }
            },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }     
}