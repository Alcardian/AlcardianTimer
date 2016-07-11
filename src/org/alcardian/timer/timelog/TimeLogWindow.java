package org.alcardian.timer.timelog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import alcardianTimer.TimeManager;

@SuppressWarnings("serial")
public class TimeLogWindow extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JTextArea textArea= new JTextArea(16,20);
	private JScrollPane scroll = new JScrollPane(textArea);
	
	private ArrayList<TimeLog> timeLog = new ArrayList<TimeLog>();
	
	public TimeLogWindow(){
		add(mainPanel);
		
		setResizable(false);
		setTitle("Log Viewer");
		setSize(240, 300);	// Set the Frame size
		setLocationRelativeTo(null);	// Move the window to the middle of the screen
		
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		textArea.setText("");
		textArea.setEditable(false);
		
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(scroll, c);
	}
	
	public void displayLogs(){
		textArea.setText("");
		
		for(int a=0; a<timeLog.size();a++){
			textArea.setText(textArea.getText() + "\n" + formatLog(timeLog.get(a)));
		}
	}
	
	public String formatLog(TimeLog timeLog){
		return (timeLog.getDate() + " - " + TimeManager.formatTime(timeLog.getTime()));
	}
	
	public void updateLogs(){
		
	}
	
	/**
	 * Function to merge two logs.
	 * @param logs
	 * @param index1
	 * @param index2
	 * @return
	 */
	public ArrayList<TimeLog> mergeLogs(ArrayList<TimeLog> logs, int index1, int index2){
		int sum = (logs.get(index1).getTime() + logs.get(index2).getTime());	//Combines the time from both logs in new variable.
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(index1);
		temp.add(index2);
		
		ArrayList<TimeLog> newList = removeLogs(logs, temp);	//remove old logs
		TimeLog tempLog = new TimeLog(logs.get(index1).getDate(), sum, logs.get(index1).getMode());	//create new merged log
		newList.add(tempLog);	//add new log
		
		newList = sortLogsAfterDate(newList);
		
		return newList;
	}
	
	/**
	 * Removes the TimeLogs at the indexes defined in the removeIndex array.
	 * @param logs
	 * @param removeIndex
	 * @return
	 */
	public ArrayList<TimeLog> removeLogs(ArrayList<TimeLog> logs, ArrayList<Integer> removeIndex){
		ArrayList<TimeLog> newList = new ArrayList<TimeLog>();
		for(int a=0; a<logs.size(); a++){
			boolean flag = true;	//if log at position a is not on the removeIndex list this is true and it adds the log to new list.
			for(int b=0; b<removeIndex.size(); b++){
				if(a == removeIndex.get(b)){	//if this index is on the list
					flag = false;	//set flag to false, will prevent this log from being written to newlist.
				}
			}
			if(flag){
				newList.add(logs.get(a));
			}
		}
		return newList;
	}
	
	/**
	 * Sorts the ArrayList.
	 * @param logs
	 * @return
	 */
	public ArrayList<TimeLog> sortLogsAfterDate(ArrayList<TimeLog> logs){
		ArrayList<TimeLog> tempLogs = new ArrayList<TimeLog>();
		String[] stringArray = new String[logs.size()];	//creating an Array of Strings to be used for sorting
		
		for(int a=0; a<logs.size(); a++){	//adds all logs to the new Array as strings
			stringArray[a] = logs.get(a).TimeLogToString();
		}
		
		Arrays.sort(stringArray);	//sort the string array
		
		for(int a=0; a<stringArray.length; a++){	//adds all logs from the new array to a ArrayList
			tempLogs.add(new TimeLog(stringArray[a]));
		}
		return tempLogs;
	}
}
