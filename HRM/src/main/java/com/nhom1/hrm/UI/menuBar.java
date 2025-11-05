package com.nhom1.hrm.UI;

import javax.swing.JMenuItem;


public class menuBar {
    public static void switchCardOnClick(JMenuItem aboutItem, cards deck, String cardName) {
        aboutItem.addActionListener(e -> deck.showCard(cardName));
    }
}
