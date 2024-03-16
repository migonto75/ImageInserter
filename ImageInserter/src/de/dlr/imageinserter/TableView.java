package de.dlr.imageinserter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.dlr.controller.PicObject;
import de.dlr.controller.ProgramManager;
import de.dlr.database.Database;

public class TableView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File dir;
	private String dirPath;
	private JLabel lblDirPath;
	private JTable table;
	private DefaultTableModel tableModel;
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton rdbtnName, rdbtnDatentyp, rdbtnGroesse, rdbtnChecksum;
	private DefaultTableCellRenderer cr;
	public JProgressBar bar;
	private int[] sr;
	private ArrayList<PicObject> liste;
	private JLabel lblFileInfo;
	

	/**
	 * Create the panel.
	 */
	public TableView(ProgramManager pm) {
		setLayout(null);
		setBounds(100, 100, 800, 600);
		
		bg.add(rdbtnName);
		bg.add(rdbtnDatentyp);
		bg.add(rdbtnGroesse);
		bg.add(rdbtnChecksum);
		
		JLabel lblSort = new JLabel("Sortieren nach  >>>");
		lblSort.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSort.setBounds(82, 431, 120, 20);
		add(lblSort);
		
		rdbtnName = new JRadioButton("Name");
		bg.add(rdbtnName);
		rdbtnName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
				table.setRowSorter(sorter);
				List<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			
			}
		});
		rdbtnName.setHorizontalAlignment(SwingConstants.TRAILING);
		rdbtnName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		rdbtnName.setBounds(260, 431, 60, 20);
		add(rdbtnName);

		rdbtnDatentyp = new JRadioButton("Datentyp");
		bg.add(rdbtnDatentyp);
		rdbtnDatentyp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
				table.setRowSorter(sorter);
				List<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
				sorter.setSortKeys(sortKeys);
				sorter.sort();
				
			}
		});
		rdbtnDatentyp.setHorizontalAlignment(SwingConstants.TRAILING);
		rdbtnDatentyp.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		rdbtnDatentyp.setBounds(360, 431, 80, 20);
		add(rdbtnDatentyp);

		rdbtnGroesse = new JRadioButton("Gr\u00F6sse");
		bg.add(rdbtnGroesse);
		rdbtnGroesse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
				table.setRowSorter(sorter);
				List<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
				sorter.setSortKeys(sortKeys);
				sorter.sort();
				
			}
		});
		rdbtnGroesse.setHorizontalAlignment(SwingConstants.TRAILING);
		rdbtnGroesse.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		rdbtnGroesse.setBounds(480, 431, 70, 20);
		add(rdbtnGroesse);

		rdbtnChecksum = new JRadioButton("Checksum");
		bg.add(rdbtnChecksum);
		rdbtnChecksum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
				table.setRowSorter(sorter);
				List<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(5, SortOrder.ASCENDING));
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
		});
		rdbtnChecksum.setHorizontalAlignment(SwingConstants.TRAILING);
		rdbtnChecksum.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		rdbtnChecksum.setBounds(590, 431, 90, 20);
		add(rdbtnChecksum);

		JButton btnDelete = new JButton("L\u00F6schen");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sr = table.getSelectedRows();
				int countSelectedRows = table.getSelectedRowCount();
				for(int i = countSelectedRows; i > 0; i--) {
					tableModel.removeRow(sr[i-1]);
				}
			}
		});
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnDelete.setBounds(580, 470, 100, 20);
		add(btnDelete);

		JLabel lblSelectedDelete = new JLabel("Ausgew\u00E4hlte Dateien l\u00F6schen  >>>");
		lblSelectedDelete.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSelectedDelete.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSelectedDelete.setBounds(325, 470, 250, 20);
		add(lblSelectedDelete);

		JLabel lblAllData = new JLabel("Folgende Dateien wurden aus den gew\u00E4hlten Verzeichnissen eingelesen:");
		lblAllData.setHorizontalAlignment(SwingConstants.CENTER);
		lblAllData.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblAllData.setBounds(80, 125, 600, 20);
		add(lblAllData);

		JButton btnUploadData = new JButton("Dateien hochladen");
		btnUploadData.setVisible(false);
		btnUploadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel = (DefaultTableModel) table.getModel();
				int count = tableModel.getRowCount();
				for (int i = 0; i < count; i++) {
					int id = (int)tableModel.getValueAt(i, 0);
					PicObject objectPic =  PicObject.getPicObject(liste, id);
					if(objectPic != null) {
						byte[] picData = InputData.getByteArrayFromFile(objectPic.getImgPath());
						Database.insertIntoImageInserter(picData, objectPic.getImgName(), objectPic.getImgType(), objectPic.getImgSize(), objectPic.getImgChecksum());
					}					
				}				
			}
		});
		
		JLabel lblSelectToDelete = new JLabel("W\u00E4hlen sie die zu l\u00F6schenden Dateine aus.");
		lblSelectToDelete.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectToDelete.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSelectToDelete.setBounds(80, 150, 600, 20);
		add(lblSelectToDelete);

		btnUploadData.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnUploadData.setBounds(80, 470, 140, 20);
		add(btnUploadData);

		bar = new JProgressBar();
		bar.setBounds(80, 380, 600, 20);
		bar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		//bar.setValue(0);
		bar.setStringPainted(true);
		//bar.setIndeterminate(true);
		//bar.setForeground(Color.GREEN);
		
							
		add(bar);
		
		JButton btnSelectDir = new JButton("Bitte Verzeichnis ausw\u00E4hlen");
		btnSelectDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dir = InputData.getDlgDirectory();
				dirPath = dir.getAbsolutePath();
				lblDirPath.setText(dirPath);
				btnUploadData.setVisible(true);
				liste = InputData.getAllFilesFromDirectoryExtend(dirPath, bar, lblFileInfo);				
				InputData.checkDoubleFiles(liste);	
				//ProgressBar.ProgressBar();
				for (int i = 0; i < liste.size(); i++) {
					liste.get(i).setId(i+1);					
					liste.get(i).setImgInfo();
				}
				insertListToTable(liste);
			}
		});

		btnSelectDir.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnSelectDir.setBounds(270, 40, 200, 23);
		add(btnSelectDir);

		lblDirPath = new JLabel();
		lblDirPath.setHorizontalAlignment(SwingConstants.CENTER);
		lblDirPath.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblDirPath.setBounds(80, 80, 600, 20);
		lblDirPath.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(lblDirPath);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(80, 175, 600, 200);
		add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "BIld", "Name", "Datentyp", "Größe in Byte", "Checksum" }) {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, ImageIcon.class, String.class, String.class, Long.class,
					String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(3).setPreferredWidth(116);
		table.setRowHeight(41);
		scrollPane.setViewportView(table);
		
		lblFileInfo = new JLabel("In progress...");
		lblFileInfo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblFileInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileInfo.setBounds(82, 400, 598, 20);
		add(lblFileInfo);
		cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(JLabel.CENTER);		
		JLabel renderer = ((JLabel)table.getDefaultRenderer(Object.class));
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	// ----------------------------------------------------------------------------------------------------//

	public void insertListToTable(ArrayList<PicObject> list) {
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (PicObject picObject : list) {
			if(!picObject.isIfDouble()) {
				tableModel.addRow(new Object[] {picObject.getId(), picObject.getImg(), picObject.getImgName(), picObject.getImgType(), picObject.getImgSize(), picObject.getImgChecksum()});
			}			
		}
	}
	
	public List<String> fullPath(String dirPath) {
		List<String> result = null;
		try {
			@SuppressWarnings("resource")
			Stream<Path> walk = Files.walk(Paths.get(dirPath));
			result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void updateBar(int newValue) {
	    bar.setValue(newValue);
	  }
}
