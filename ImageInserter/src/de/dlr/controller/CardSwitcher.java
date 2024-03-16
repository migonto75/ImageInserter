package de.dlr.controller;

import java.awt.CardLayout;

import javax.swing.JLayeredPane;

public class CardSwitcher {
	
	public void switchCard (CardLayout cl, JLayeredPane lp, String card) {
		cl.show(lp, card);
	}

}
