package alcardianTimer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class AlcardianTimer_Frame extends JFrame implements ActionListener {
	public static String version = "1.07";	//Version value, change this to change the displayed version on all the places in the program.
	public static ArrayList<String> eventLog = new ArrayList<String>();	//A log of events happening during the run of the program.
	public static String path = "";
	public static int windowSize = 0;
	
	private javax.swing.Timer timer = new javax.swing.Timer(1000,this);
	private ArrayList<DisplayPanel> display = new ArrayList<DisplayPanel>();
	private JPanel mainPanel = new JPanel();
	
	private FileManager fileIO = new FileManager();	//to read/write to file
	
	private int displays = 4;	//to store how many displays to open
	private ArrayList<String> mode = new ArrayList<String>();	//displays last mode
	
	//New items from 1.06
	
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu fileMenu = new JMenu("File");
	private JMenu windowMenu = new JMenu("Window");
	private JMenu helpMenu = new JMenu("Help");
	
	JMenuItem closeItem = new JMenuItem("Close");
	JMenuItem saveSettingsItem = new JMenuItem("Save Settings");
	
	JMenuItem show1Item = new JMenuItem("Show 1 Display");
	JMenuItem show2Item = new JMenuItem("Show 2 Display");
	JMenuItem show4Item = new JMenuItem("Show 4 Display");
	
	JMenuItem getScreenLoc = new JMenuItem("Get Window Screen Location");
	
	private int height = 220;
	
	
	public AlcardianTimer_Frame(){
		ArrayList<String> buffer = new ArrayList<String>();
		buffer = fileIO.ReadFile(path + "AlcardianTimer_settings " + version +".txt");
		
		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		menuBar.add(helpMenu);
		
		fileMenu.add(closeItem);
		fileMenu.add(saveSettingsItem);
		windowMenu.add(show1Item);
		windowMenu.add(show2Item);
		windowMenu.add(show4Item);
		helpMenu.add(getScreenLoc);
		
		if(!buffer.isEmpty()){
			String[] bufferParts = buffer.get(0).split("@");
			if(bufferParts.length == 2){
				if(TimeManager.isNumber(bufferParts[0], false)){
					displays = Integer.parseInt(bufferParts[0]);	//set number of displays to bufferParts[0]
					System.out.println("Number of displays: " + bufferParts[0] + ".");
				}
				String[] timeParts = bufferParts[1].split("#");
				for(int a=0; a<timeParts.length; a++){
					System.out.println("Setting for display " + (a+1) + ": " + timeParts[a] + ".");
				}
				for(int a=0; a<timeParts.length;a++){
					if(TimeManager.isNumber(timeParts[a], false)){
						if(Integer.parseInt(timeParts[a]) < 5 && Integer.parseInt(timeParts[a]) > 0){
							mode.add(timeParts[a]);
						}else{
							mode.add("2");
						}
					} else{
						mode.add("2");
					}
				}
			}
		}
		
		if(windowSize>0){
			setWindowSize(windowSize);
		}else{
			setWindowSize(displays);
		}
		
		
		/*
		else{
			setSize(((frameWidth * displays)), 220); // Set the Frame size
		}
		*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Alcardian Timer " + version);
		//setResizable(false);
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//c.insets = new Insets(0,10,0,0);
		c.insets = new Insets(0,10,0,10);
		for(int a=0; a<displays; a++){
			display.add(new DisplayPanel());
			c.gridx = a;
			c.gridy = 0;
			mainPanel.add(display.get(a), c);
		}
		
		add(new JScrollPane(mainPanel));
		
		if(displays == mode.size()){	//if there is 1 mode for each display.
			System.out.println("Found modes for each display.");
			for(int a=0; a<mode.size(); a++){
				display.get(a).setMode(Integer.parseInt(mode.get(a)));
			} 
		}else{	//if there are not 1 mode for each display.
			System.out.println("Error in display settings...");	//A huge block of message to tell what was the problem.
			//System.out.println(buffer.get(0));
			if(!mode.isEmpty()){
				if(mode.size()<displays){
					System.out.println("Some of the values for display mode exist, using them and filling in the rest with default values");
				}else{
					System.out.println("More values for displays than there are displays, using as many of them as posible");
				}
				
			}else{
				System.out.println("no values for display mode, using default values");
			}
			
			for(int a=0; a<display.size(); a++){
				if(a < mode.size()){
					display.get(a).setMode(Integer.parseInt(mode.get(a)));
				}else if(a < 4){
					display.get(a).setMode(a+1);
				} else{
					display.get(a).setMode(2);
				}
			}
		}
		
		timer.start();
		
		MenuListener menuListener = new MenuListener();
		closeItem.addActionListener(menuListener);
		show1Item.addActionListener(menuListener);
		show2Item.addActionListener(menuListener);
		show4Item.addActionListener(menuListener);
		saveSettingsItem.addActionListener(menuListener);
		getScreenLoc.addActionListener(menuListener);
	}
	
	
	public void setWindowSize(int numberOfDisplays){
		if(numberOfDisplays == 1){
			setSize(240, height);	// Set the Frame size
		} else if(numberOfDisplays <= 3){
			setSize(461, height);
		} else{
			setSize(905, height);
		}
	}
	
	public void saveSettings(){
		String temp = "";
		temp += displays + "@";
		if(displays == display.size()){	//if there are as many displays as display settings.
			for(int a=0; a<display.size(); a++){
				if(a!=0){
					temp += "#" + display.get(a).getMode();
				}else{
					temp += display.get(a).getMode();
				}
				
			}
		}else if(displays < display.size()){	//if there are less displays than settings.
			if(displays>0){	//if displays are more than 0
				for(int a=0; a<displays; a++){
					if(a!=0){	//adding #
						temp += "#";
					}
					temp += display.get(a).getMode();
				}
			}else{	//if there are no displays, set standard.
				temp = "4@1#2#3#4";
			}
			
		}else{	//else, if there are more displays than settings.
			for(int a=0; a<displays; a++){
				if(a!=0){
					temp += "#";
				}
				if(a<display.size()){	//if there are display settings left.
					temp += display.get(a).getMode();
				}else{	//else set standard value.
					temp += "2";
				}
			}
		}
		//to do
		ArrayList<String> buffer = new ArrayList<String>();
		buffer.add(temp);
		fileIO.writeToFile(buffer, path + "AlcardianTimer_settings " + version +".txt");
	}
	
	private class MenuListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == closeItem){
				System.out.println("");
				System.out.println("Program terminated by user pressing close in menu.");
				System.exit(0);
			}else if(e.getSource() == show1Item){
				setWindowSize(1);
			}else if(e.getSource() == show2Item){
				setWindowSize(2);
			}else if(e.getSource() == show4Item){
				setWindowSize(4);
			}else if(e.getSource() == saveSettingsItem){
				saveSettings();
			}else if(e.getSource() == getScreenLoc){
				Point p = getLocationOnScreen();
				JOptionPane.showMessageDialog(null, "The Programs top-left corner have the coordinates: \n" +
													"X: " + p.x + "\n" + "Y: " + p.y);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int a=0; a<display.size(); a++){
			display.get(a).update();
		}
	}
}
