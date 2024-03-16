package de.dlr.imageinserter;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.dlr.controller.ProgramManager;

public class Info extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String logoDLR = "S:/Projekte/Programmierung/Praktikum/ImageInserter/src/res/logo_dlr.png";
	

	/**
	 * Create the panel.
	 */
	public Info(ProgramManager pm) {
		setFont(new Font("Times New Roman", Font.PLAIN, 14));
		setLayout(null);
		setBounds(100, 100, 800, 600);
		
		JLabel lblTitel = new JLabel("ImageInserter");
		lblTitel.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitel.setFont(new Font("Times New Roman", Font.ITALIC, 60));
		lblTitel.setBounds(0, 200, 800, 70);
		add(lblTitel);
		
		JLabel lblBy = new JLabel("by");
		lblBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblBy.setFont(new Font("Times New Roman", Font.ITALIC, 25));
		lblBy.setBounds(0, 270, 800, 40);
		add(lblBy);
		
		JLabel lblDeveloper = new JLabel("Miguel Gonzales-Tounsi");
		lblDeveloper.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeveloper.setFont(new Font("Times New Roman", Font.ITALIC, 25));
		lblDeveloper.setBounds(0, 310, 800, 40);
		add(lblDeveloper);
		
		ImageIcon imageIcon = new ImageIcon(logoDLR); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(500, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		JLabel lblLogo = new JLabel(imageIcon);
		lblLogo.setBounds(0, 0, 800, 120);
		add(lblLogo);
	}

}

