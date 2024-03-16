package de.dlr.imageinserter;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.dlr.controller.ProgramManager;
import de.dlr.controller.Utils;
import de.dlr.database.Database;

public class Main extends JFrame {
	
	private static ProgramManager pm;
	public static final TableView readData = new TableView(pm);
	private JPanel contentPane;
	public static Main frame;
		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//-----------------------------------------------------------------------------------------------------------------------//
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Utils.test();
		Database.createDatabase();
		Database.createTable();

		EventQueue.invokeLater(new Runnable() {
	public void run() {
		try {
			frame = new Main();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
});
	}
	//-----------------------------------------------------------------------------------------------------------------------//
	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("ImageInserter");
		pm = new ProgramManager();
		setBounds(100, 100, 800, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.add(pm.getLayeredPane());
		contentPane.setLayout(pm.getCardLayout());
		pm.getLayeredPane().setLayout(pm.getCardLayout());
		
		//----------------------------------------------------------------------------------------------------------------------//
		/**
		 * Objekte erzeugen 
		 */
		Start start = new Start(pm);
		//Panel(Card) dem LayeredPane hinzuf�gen
		pm.getLayeredPane().add(start, "Start");
		
		Info info = new Info(pm);
		pm.getLayeredPane().add(info, "Info");
		
		//final TableView readData = new TableView(pm);
		pm.getLayeredPane().add(readData, "ReadData");
		
		
		//-----------------------------------------------------------------------------------------------------------------------//
		/**
		 * Men� erstellen
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(5, 5, 5, 5));
		menuBar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		setJMenuBar(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		mnDatei.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		menuBar.add(mnDatei);

		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmReadData = new JMenuItem("Dateien einlesen");
		mntmReadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.getCardSwitcher().switchCard(pm.getCardLayout(), pm.getLayeredPane(), "ReadData");
			}
		});
		
		mntmReadData.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		mnDatei.add(mntmReadData);
		mntmBeenden.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		mnDatei.add(mntmBeenden);

		JMenu mnHilfe = new JMenu("?");
		mnHilfe.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		menuBar.add(mnHilfe);
		
		JMenuItem mntmInfo = new JMenuItem("Info");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.getCardSwitcher().switchCard(pm.getCardLayout(), pm.getLayeredPane(), "Info");
			}
		});
		mntmInfo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		mnHilfe.add(mntmInfo);
		
		
		//-----------------------------------------------------------------------------------------------------------------------//
		
	}
	
}
