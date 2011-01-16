package wiibugger.pc.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
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
import wiibugger.pc.StopWiibuggerAction;
import wiibugger.pc.Wiibugger;

public class UserInterface {
	
	private static Font labelFont;
	private static JFrame mainWindow;
	private static JButton runButton, stopButton, scanWiimoteButton, scanNXTButton;
	private static JSplitPane splitPane;
	private static JLabel wiimoteLabel, nxtLabel;
	private static JList wiimoteList, nxtList;
	private static JPanel wiimotePanel, nxtToolbar, nxtPanel, runPanel;
	private static RunWiibuggerAction runWiibuggerAction;
	private static StopWiibuggerAction stopWiibuggerAction;
	private static ScanNxtAction scanNXTAction;
	private static ScanWiimoteAction scanWiimoteAction;
	private static JFrame runningWindow;
	
	private static Font getLabelFont() {
		if (UserInterface.labelFont == null) {
			labelFont = new Font(null, Font.BOLD, 18);
		}
		return UserInterface.labelFont;
	}
	
	public static void showRunningWindow() {
		runningWindow = new JFrame("Wiibugger running...");
		runningWindow.setSize(640, 480);
		runningWindow.add(getStopButton());
		runningWindow.addWindowListener(new WindowAdapter() {			
			@Override
			public void windowClosed(WindowEvent e) {
				getStopWiibuggerAction().actionPerformed(null);
			}
		});
		runningWindow.setVisible(true);
	}

	public static JFrame getMainWindow() {
		if (UserInterface.mainWindow == null) {
			mainWindow = new JFrame(Wiibugger.applicationTitle);
			mainWindow.setSize(500, 300);
			
			JPanel mainWindowPanel = new JPanel(new BorderLayout());
			mainWindowPanel.add(getSplitPane(), BorderLayout.CENTER);
			mainWindowPanel.add(getRunPanel(), BorderLayout.SOUTH);
			mainWindowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
			
			mainWindow.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent evt) {
					Wiibugger.exit();
				}
			});
			
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

	public static JList getNXTList() {
		if (UserInterface.nxtList == null) {
			nxtList = new JList();
			nxtList.setModel(Wiibugger.getNXTList());
		}
		return UserInterface.nxtList;
	}
	
	private static JPanel getNXTPanel() {
		if (UserInterface.nxtPanel == null) {
			nxtPanel = new JPanel(new BorderLayout());
			nxtPanel.add(getNxtToolbar(), BorderLayout.NORTH);			
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

	private static Component getStopButton() {
		if (UserInterface.stopButton == null) {
			stopButton = new JButton(getStopWiibuggerAction());
		}
		return UserInterface.stopButton;
	}
	
	private static JPanel getRunPanel() {
		if(runPanel == null) {
			runPanel = new JPanel();
			runPanel.add(getRunButton());
		}
		return UserInterface.runPanel;
	}
	
	private static JButton getScanNXTButton() {
		if (UserInterface.scanNXTButton == null) {
			scanNXTButton = new JButton(getScanNXTAction());
		}
		return UserInterface.scanNXTButton;
	}

	private static JButton getScanWiimotesButton() {
		if (UserInterface.scanWiimoteButton == null) {
			scanWiimoteButton = new JButton(getScanWiimotesAction());
		}
		return UserInterface.scanWiimoteButton;
	}

	private static JSplitPane getSplitPane() {
		if (UserInterface.splitPane == null) {
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			splitPane.setLeftComponent(getWiimotePanel());
			splitPane.setRightComponent(getNXTPanel());
			splitPane.setDividerLocation(230);
			splitPane.setBorder(BorderFactory.createEmptyBorder());
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
	
	public static JList getWiimoteList() {
		if (UserInterface.wiimoteList == null) {
			wiimoteList = new JList();
			wiimoteList.setModel(Wiibugger.getWiimoteList());
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
	
	private static JPanel getNxtToolbar() {
		if (UserInterface.nxtToolbar == null) {
			nxtToolbar = new JPanel(new BorderLayout());
			nxtToolbar.add(getNXTLabel(), BorderLayout.WEST);
			nxtToolbar.add(getScanNXTButton(), BorderLayout.EAST);
		}
		return UserInterface.nxtToolbar;
	}

	public static RunWiibuggerAction getRunWiibuggerAction() {
		if (UserInterface.runWiibuggerAction == null) {
			runWiibuggerAction = new RunWiibuggerAction();
		}
		return UserInterface.runWiibuggerAction;
	}
	
	public static StopWiibuggerAction getStopWiibuggerAction() {
		if(UserInterface.stopButton == null) {
			stopWiibuggerAction = new StopWiibuggerAction();
		}
		return UserInterface.stopWiibuggerAction;
	}

	public static ScanWiimoteAction getScanWiimotesAction() {
		if (scanWiimoteAction == null) {
			scanWiimoteAction = new ScanWiimoteAction();
		}
		return scanWiimoteAction;
	}
	
	public static ScanNxtAction getScanNXTAction() {
		if (scanNXTAction == null) {
			scanNXTAction = new ScanNxtAction();
		}
		return scanNXTAction;
	}

	public static void closeRunningWindow() {
		runningWindow.setVisible(false);
		runningWindow = null;
		getMainWindow().setVisible(true);
	}
	
}
