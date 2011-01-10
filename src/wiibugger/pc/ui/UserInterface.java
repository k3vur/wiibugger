package wiibugger.pc.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import wiibugger.pc.RunWiibuggerAction;
import wiibugger.pc.ScanNxtAction;
import wiibugger.pc.ScanWiimoteAction;
import wiibugger.pc.Wiibugger;

public class UserInterface {
	
	private static Font labelFont;
	private static JFrame mainWindow;
	private static JButton runButton, stopButton, scanWiimoteButton, scanNXTButton;
	private static JSplitPane splitPane;
	private static JLabel wiimoteLabel, nxtLabel;
	private static JList wiimoteList, nxtList;
	private static JPanel wiimotePanel, nxtPanel, runPanel;
	
	private static Font getLabelFont() {
		if (UserInterface.labelFont == null) {
			labelFont = new Font(null, Font.BOLD, 18);
		}
		return UserInterface.labelFont;
	}

	private static JFrame getMainWindow() {
		if (UserInterface.mainWindow == null) {
			mainWindow = new JFrame(Wiibugger.applicationTitle);
			mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainWindow.setSize(800, 600);
			
			JPanel mainWindowPanel = new JPanel(new BorderLayout());
			mainWindowPanel.add(getSplitPane(), BorderLayout.CENTER);
			mainWindowPanel.add(getRunPanel(), BorderLayout.SOUTH);
			
			mainWindow.add(mainWindowPanel);
		}
		return UserInterface.mainWindow;
	}
	
	private static JLabel getNXTLabel() {
		if (UserInterface.nxtLabel == null) {
			nxtLabel = new JLabel("NXTs");
			nxtLabel.setFont(getLabelFont());
		}
		return UserInterface.nxtLabel;
	}

	private static JList getNXTList() {
		if (UserInterface.nxtList == null) {
			nxtList = new JList();

			nxtList.setModel(Wiibugger.getNXTList());
			nxtList.setCellRenderer(new NXTListCellRenderer());
		}
		return UserInterface.nxtList;
	}
	
	private static JPanel getNXTPanel() {
		if (UserInterface.nxtPanel == null) {
			nxtPanel = new JPanel(new BorderLayout());
			
			JPanel topPanel = new JPanel(new BorderLayout());
			topPanel.add(getNXTLabel(), BorderLayout.WEST);
			topPanel.add(getScanNXTButton(), BorderLayout.EAST);
			
			nxtPanel.add(topPanel, BorderLayout.NORTH);			
			nxtPanel.add(new JScrollPane(getNXTList()), BorderLayout.CENTER);
		}
		return UserInterface.nxtPanel;
	}
	
	private static Component getRunButton() {
		if (UserInterface.runButton == null) {
			runButton = new JButton("Run Wiibugger!");
			runButton.addActionListener(new RunWiibuggerAction());
		}
		return UserInterface.runButton;
	}

	private static Component getStopButton() {
		if (UserInterface.stopButton == null) {
			stopButton = new JButton("Stop Wiibugger :-(");
		}
		return UserInterface.stopButton;
	}
	
	private static JPanel getRunPanel() {
		if(runPanel == null) {
			runPanel = new JPanel();
			runPanel.add(getRunButton());
			runPanel.add(getStopButton());
		}
		return UserInterface.runPanel;
	}
	
	private static JButton getScanNXTButton() {
		if (UserInterface.scanNXTButton == null) {
			scanNXTButton = new JButton("Scan");
			scanNXTButton.addActionListener(new ScanNxtAction());
		}
		return UserInterface.scanNXTButton;
	}

	private static JButton getScanWiimotesButton() {
		if (UserInterface.scanWiimoteButton == null) {
			scanWiimoteButton = new JButton(new ScanWiimoteAction());
		}
		return UserInterface.scanWiimoteButton;
	}

	private static JSplitPane getSplitPane() {
		if (UserInterface.splitPane == null) {
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			splitPane.setLeftComponent(getWiimotePanel());
			splitPane.setRightComponent(getNXTPanel());
			splitPane.setDividerLocation(0.5);
		}
		return UserInterface.splitPane;
	}

	private static JLabel getWiiLabel() {
		if (UserInterface.wiimoteLabel == null) {
			wiimoteLabel = new JLabel("Wiimotes");
			wiimoteLabel.setFont(getLabelFont());
		}
		return UserInterface.wiimoteLabel;
	}
	
	private static JList getWiimoteList() {
		if (UserInterface.wiimoteList == null) {
			wiimoteList = new JList();
			wiimoteList.setModel(Wiibugger.getWiimoteList());
			wiimoteList.setCellRenderer(new WiimoteListCellRenderer());
		}
		return UserInterface.wiimoteList;
	}

	private static JPanel getWiimotePanel() {
		if (UserInterface.wiimotePanel == null) {
			wiimotePanel = new JPanel(new BorderLayout());
			
			JPanel topPanel = new JPanel(new BorderLayout());
			topPanel.add(getWiiLabel(), BorderLayout.WEST);
			topPanel.add(getScanWiimotesButton(), BorderLayout.EAST);
			
			wiimotePanel.add(topPanel, BorderLayout.NORTH);			
			wiimotePanel.add(new JScrollPane(getWiimoteList()), BorderLayout.CENTER);
		}
		return UserInterface.wiimotePanel;
	}
	
	public static void init() {
		
		/*
		 * Some Mac-Settings
		 */
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", Wiibugger.applicationTitle);
		
    	/*
    	 * Set native look and feel.
    	 */
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Could not use native look and feel");
		}
		
		getMainWindow().setVisible(true);
	}
	
}
