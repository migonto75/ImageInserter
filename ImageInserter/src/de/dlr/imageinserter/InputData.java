package de.dlr.imageinserter;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import de.dlr.controller.PicObject;

public class InputData {
	private static String fileName;
	private static String fileType;
	private static long fileSize;
	private static String fileChecksum;
	public static JProgressBar pbar;	
		

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * scaleImage - Skaliert die img-Files auf die gewünschte Größe
	 * 
	 * @param file
	 * @return
	 */
	public static ImageIcon loadAndScaleImage(String file) {
		ImageIcon imageIcon = new ImageIcon(file); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		return imageIcon = new ImageIcon(newimg);
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * getNameFromFile - Der Dateiname wird ohne Dateiendung ausgelesen
	 * 
	 * @param file
	 * @return
	 */
	public static String getNameFromFile(String file) {
		File f = new File(file);
		fileName = f.getName();
		String newFileName = fileName.substring(0, fileName.indexOf('.'));
		return newFileName;
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * getFileExtension - Dateiendung wird ausgelesen
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileExtension(String file) {
		fileType = new File(file).getName();
		if (fileType.lastIndexOf(".") != -1 && fileType.lastIndexOf(".") != 0)
			return fileType.substring(fileType.lastIndexOf(".") + 1);
		else
			return "";
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * getSizeFromFile
	 * 
	 * @param file
	 * @return
	 */
	public static long getSizeFromFile(String file) {
		FileInputStream fileInStr = null;
		try {
			File imgFile = new File(file); // Datei wird geöffnet
			fileInStr = new FileInputStream(imgFile); // Daten werden übertragen
			fileSize = imgFile.length();
			fileInStr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden!" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Zugriff auf die Datei nicht möglich." + e.getMessage());
		}
		return fileSize;
	}

	//-----------------------------------------------------------------------------------------------------//

	/**
	 * getByteArrayFromFile
	 * @param filename
	 * @return
	 */
	public static byte[] getByteArrayFromFile(String filename) {
		byte[] result = null;
		FileInputStream fileInStr = null;
		try {
			File imgFile = new File(filename); // Datei wird geöffnet
			fileInStr = new FileInputStream(imgFile); // Daten werden übertragen
			long imgSize = imgFile.length();
			result = new byte[(int) imgSize];
			fileInStr.read(result);
			fileInStr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden!" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Zugriff auf die Datei nicht möglich." + e.getMessage());
		}
		return result;
	}
		
	// ----------------------------------------------------------------------------------------------------//
	
	/**
	 * checksum
	 * 
	 * @param data
	 * @return
	 */
	public static String checksum(byte[] data) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(data);
			String sha256hex = bytesToHex(hash);
			return sha256hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * getChecksumFromFile
	 * 
	 * @param file
	 */
	public static String getChecksumFromFile(String file) {
		PicObject fileExChecksum = new PicObject(new File(file));
		try {
			byte[] data = getByteArrayFromFile(fileExChecksum.getFile().getCanonicalPath());
			fileChecksum = checksum(data);
			fileExChecksum.setImgChecksum(fileChecksum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileExChecksum.getImgChecksum();
	}

	// ----------------------------------------------------------------------------------------------------//

	public static void getCheckSumFromFile(PicObject file) {
		try {
			byte[] data = getByteArrayFromFile(file.getFile().getCanonicalPath());
			String checksum = checksum(data);
			file.setImgChecksum(checksum);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * bytesToHex
	 * 
	 * @param hash
	 * @return
	 */
	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * deleteDoubleFiles
	 * 
	 * @param list
	 */
	public static ArrayList<PicObject> deleteDoubleFiles(ArrayList<PicObject> list) {
		ArrayList<PicObject> uniqueFiles = new ArrayList<PicObject>();
		for (PicObject picObject : list) {
			int isContain = 0;
			for (int i = 0; i < list.size(); i++) {
				if (picObject.getImgChecksum().contains(list.get(i).getImgChecksum())) {
					++isContain;
					if (isContain == 2) {
						list.remove(list.get(i));
					}
				}
			}
			System.out.println(isContain);
		}
		System.out.println("----");
		System.out.println(list.size());
		return uniqueFiles;
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * getDlgDirectory
	 * 
	 * @return
	 */
	// Erfragt beim Benutzer das Startverzeichnis, gibt null zurück, wenn der Dialog
	// abgebrochen wurde
	public static File getDlgDirectory() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Start-Verzeichnis auswählen");
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter standardFilter = new FileNameExtensionFilter("Nur Verzeichnisse", "*.*");
		chooser.addChoosableFileFilter(standardFilter);
		int answer = chooser.showOpenDialog(null);
		if (answer == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * checkDoubleFiles
	 * 
	 * @param fileList
	 */
	public static void checkDoubleFiles(ArrayList<PicObject> fileList) {
		for (int i = 0; i < fileList.size(); i++) {
			PicObject fileEx = fileList.get(i);
			for (int j = 0; j < fileList.size(); j++) {
				if (i == j || fileEx.isIfDouble()) {
					continue;
				}
				PicObject fileEx2 = fileList.get(j);
				if (fileEx.getImgChecksum().equals(fileEx2.getImgChecksum())) {
					fileEx2.setIfDouble(true);
					fileEx2.setDoubleIndex(i);
				}
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * getAllFilesFromDirectory
	 * 
	 * @param file
	 * @param list
	 * @return
	 */
	public static ArrayList<File> getAllFilesFromDirectory(File file, ArrayList<File> list) {
		if (file == null || list == null || !file.isDirectory())
			return null;
		File[] lstFiles = file.listFiles();

		for (int i = 0; i < lstFiles.length; i++) {
			if (lstFiles[i].isDirectory()) {
				getAllFilesFromDirectory(lstFiles[i], list);
			} else {
				list.add(lstFiles[i]);
			}
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * getAllFilesFromDirectoryExtend
	 * 
	 * @param dir
	 * @return
	 */
	public static ArrayList<PicObject> getAllFilesFromDirectoryExtend(String dir, JProgressBar bar, JLabel lbl) {
		
		int counter = 0;
		ArrayList<PicObject> listOfFiles = new ArrayList<PicObject>();
		ArrayList<File> lstFiles = getAllFilesFromDirectory(new File(dir), new ArrayList<File>());
		if(bar != null) {			
			bar.setValue(0);
			bar.setMinimum(0);			
			bar.setMaximum(lstFiles.size()-1);			
			pbar = bar;
		}
		for (int i = 0; i < lstFiles.size(); i++) {			
			if(lbl != null) {
				lbl.setText(lstFiles.get(i).getName());
			}
			if(bar != null) {
				//bar.setValue(counter);
				final int percent = i;
			      try {
			        SwingUtilities.invokeLater(new Runnable() {
			          public void run() {
			            updateBar(percent);
			          }
			        });
			        java.lang.Thread.sleep(100);
			      } catch (InterruptedException e) {
			    	  e.getStackTrace();
			        ;
			      }
				counter++;
				System.out.println(counter);
			}
			PicObject fileEx = new PicObject(lstFiles.get(i));
			getCheckSumFromFile(fileEx);
			listOfFiles.add(fileEx);
		}
		if(lbl != null) {
			lbl.setText("Daten erfolgreich geladen.");
		}
		return listOfFiles;
	}
	
/*public static ArrayList<PicObject> getAllFilesFromDirectoryExtend(String dir, JProgressBar bar, JLabel lbl) {
		
		int counter = 0;
		ArrayList<PicObject> listOfFiles = new ArrayList<PicObject>();
		ArrayList<File> lstFiles = getAllFilesFromDirectory(new File(dir), new ArrayList<File>());
		if(bar != null) {			
			bar.setMinimum(0);
			bar.setValue(0);
			bar.setMaximum(lstFiles.size()-1);
			pbar = bar;
		}
		for (int i = 0; i < lstFiles.size(); i++) {
			if(lbl != null) {
				lbl.setText(lstFiles.get(i).getName());
			}
			if(bar != null) {
				//bar.setValue(counter);
				final int percent = i;
			      try {
			        SwingUtilities.invokeLater(new Runnable() {
			          public void run() {
			            //Main.readData.updateBar(percent);
			        	  updateBar(percent);
			          }
			        });
			        Thread.sleep(100);
			      } catch (InterruptedException e) {
			        ;
			      }
				counter++;
			}
			PicObject fileEx = new PicObject(lstFiles.get(i));
			getCheckSumFromFile(fileEx);
			listOfFiles.add(fileEx);
		}
		if(lbl != null) {
			lbl.setText("Daten erfolgreich geladen.");
		}
		return listOfFiles;
	}*/
	
	public static void updateBar(int newValue) {
	    pbar.setValue(newValue);
	  }	
		
	//-----------------------------------------------------------------------------------------------------//

	/**
	 * countAllFilesFromDirectory
	 * 
	 * @return
	 */
	public static int countAllFilesFromDirectory(String dir) {
		ArrayList<File> lstFiles = InputData.getAllFilesFromDirectory(new File(dir), new ArrayList<File>());
		return lstFiles.size();
	}

	// ----------------------------------------------------------------------------------------------------//

	/**
	 * setCellsAlignment
	 * 
	 * @param table
	 * @param alignment
	 */
	public static void setCellsAlignment(JTable table, int alignment) {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(alignment);
		TableModel tableModel = table.getModel();
		for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
			table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
		}
	}
	
	
}