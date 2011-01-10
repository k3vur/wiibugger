package wiibugger.pc.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import wiibugger.pc.RunWiibuggerAction;
import wiibugger.pc.ScanWiimoteAction;
import wiibugger.pc.Wiibugger;

public class UserInterface {
	
	private static Font labelFont;
	private static JFrame mainWindow;
	private static JButton runButton, scanWiimoteButton, setWiimote1Button, setWiimote2Button, scanNXTButton;
	private static RunWiibuggerAction runWiibuggerAction;
	private static SetWiimoteListener setWiimoteListener;
	private static JSplitPane splitPane;
	private static JLabel wiimoteLabel, nxtLabel;
	private static JList wiimoteList, nxtList;
	private static JPanel wiimotePanel, wiimoteToolbar, nxtPanel;
	
	private static Font getLabelFont() {
		if (UserInterface.labelFont == null) {
			labelFont = new Font(null, Font.BOLD, 18);
		}
		return UserInterface.labelFont;
	}

	private static JFrame getMainWindow() {
		if (UserInterface.mainWindow == null) {
			mainWindow = new JFrame(Wiibugger.applicationTitle);
			mainWindow.setSize(800, 600);
			
			JPanel mainWindowPanel = new JPanel(new BorderLayout());
			mainWindowPanel.add(getSplitPane(), BorderLayout.CENTER);
			mainWindowPanel.add(getRunButton(), BorderLayout.SOUTH);
			
			mainWindow.add(mainWindowPanel);
			
			mainWindow.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					Wiibugger.exit();
				}
			});
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
			runButton = new JButton(getRunWiibuggerAction());
		}
		return UserInterface.runButton;
	}

	public static RunWiibuggerAction getRunWiibuggerAction() {
		if (UserInterface.runWiibuggerAction == null) {
			runWiibuggerAction = new RunWiibuggerAction();
		}
		return UserInterface.runWiibuggerAction;
	}

	private static JButton getScanNXTButton() {
		if (UserInterface.scanNXTButton == null) {
			scanNXTButton = new JButton("Scan");
			
			// TODO nxt scanbutton actionlistener
			scanNXTButton.addActionListener(null);
		}
		return UserInterface.scanNXTButton;
	}

	private static JButton getScanWiimotesButton() {
		if (UserInterface.scanWiimoteButton == null) {
			scanWiimoteButton = new JButton(new ScanWiimoteAction());
		}
		return UserInterface.scanWiimoteButton;
	}

	public static JButton getSetWiimote1Button() {
		if (UserInterface.setWiimote1Button == null) {
			setWiimote1Button = new JButton("Set 1");
			setWiimote1Button.addActionListener(getSetWiimoteListener());
		}
		return UserInterface.setWiimote1Button;
	}

	public static JButton getSetWiimote2Button() {
		if (UserInterface.setWiimote2Button == null) {
			setWiimote2Button = new JButton("Set 2");
			setWiimote2Button.addActionListener(getSetWiimoteListener());
		}
		return UserInterface.setWiimote2Button;
	}
	
	private static ActionListener getSetWiimoteListener() {
		if (UserInterface.setWiimoteListener == null) {
			setWiimoteListener = new SetWiimoteListener(getWiimoteList());
		}
		return UserInterface.setWiimoteListener;
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
			wiimoteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			wiimoteList.setCellRenderer(new WiimoteListCellRenderer());
		}
		return UserInterface.wiimoteList;
	}
	
	private static JPanel getWiimotePanel() {
		if (UserInterface.wiimotePanel == null) {
			wiimotePanel = new JPanel(new BorderLayout());			
			wiimotePanel.add(getWiimoteToolbar(), BorderLayout.NORTH);			
			wiimotePanel.add(new JScrollPane(getWiimoteList()), BorderLayout.CENTER);
		}
		return UserInterface.wiimotePanel;
	}

	private static JPanel getWiimoteToolbar() {
		if (UserInterface.wiimoteToolbar == null) {
			wiimoteToolbar = new JPanel(new BorderLayout());
			wiimoteToolbar.add(getWiiLabel(), BorderLayout.WEST);
			
			JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			buttons.add(getSetWiimote1Button());
			buttons.add(getSetWiimote2Button());
			buttons.add(getScanWiimotesButton());
			
			wiimoteToolbar.add(buttons, BorderLayout.EAST);
		}
		return UserInterface.wiimoteToolbar;
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
