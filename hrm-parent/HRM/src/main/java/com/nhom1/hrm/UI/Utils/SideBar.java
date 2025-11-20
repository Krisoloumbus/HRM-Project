/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI.Utils;

import javax.swing.JButton;

/**
 *
 * @author Kris
 */
public class SideBar {   
    
    public static void switchCardOnClick(JButton button, Cards deck, String cardName) {
        button.addActionListener(e -> deck.showCard(cardName));
    }
}
