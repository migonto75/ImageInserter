package de.dlr.imageinserter;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.dlr.controller.ProgramManager;

public class Start extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Start(ProgramManager pm) {
		setFont(new Font("Times New Roman", Font.PLAIN, 14));
		setLayout(null);
		setBounds(100, 100, 800, 600);
		
		JLabel lblNewLabel = new JLabel("ImageInserter");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblNewLabel.setBounds(0, 30, 800, 50);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Der ImageInserter wertet Bilddaten aus und speichert sie in einer Datenbank.");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 144, 800, 20);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nicht mehr ben\u00F6tigte BIlder k\u00F6nnen dann gel\u00F6scht werden.");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(0, 166, 800, 20);
		add(lblNewLabel_1_1);
	}
}
