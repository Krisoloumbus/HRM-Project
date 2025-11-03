/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JPanel;

/**
 *
 * @author Kris
 */
public class cards extends JPanel {
    
    public cards (){
        setLayout(new CardLayout());
    }

    public void addCard(String cardName, Component comp) {
        this.add(comp, cardName);
    }
    
    public void showCard(String cardName) {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, cardName);  
    }
}
