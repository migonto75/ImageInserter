package de.dlr.controller;

import java.awt.CardLayout;

import javax.swing.JLayeredPane;

public class ProgramManager {
	
	private CardLayout cardLayout;
	private JLayeredPane layeredPane;
	private CardSwitcher cardSwitcher;
	
	
	public ProgramManager() {
		cardLayout = new CardLayout();
		layeredPane = new JLayeredPane();
		cardSwitcher = new CardSwitcher();
		
		
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}

	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}

	public void setLayeredPane(JLayeredPane layeredPane) {
		this.layeredPane = layeredPane;
	}

	public CardSwitcher getCardSwitcher() {
		return cardSwitcher;
	}

	public void setCardSwitcher(CardSwitcher cardSwitcher) {
		this.cardSwitcher = cardSwitcher;
	}

		
	
}
