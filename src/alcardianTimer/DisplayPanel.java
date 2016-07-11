package alcardianTimer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class DisplayPanel extends JPanel{
	
	private JLabel DisplayLabel = new JLabel();
	private JTextField DisplayTextfield = new JTextField();
	private JButton startButton = new JButton("Start");
	private JButton resetButton = new JButton("Reset");
	private JButton editButton = new JButton("Edit");
	private JPanel buttonPanel = new JPanel();
	
	private JComboBox<String> modeBox = new JComboBox<String>(new String[]{"Clock", "Accumulator", "Countdown", "Alarm"});
	
	static int CLOCK = 1;
	static int ACCUMULATOR = 2;
	static int COUNTDOWN = 3;
	static int ALARM = 4;
	
	private int mode = 0;	//display mode, 0 = none, for others see statics
	private boolean paused = true;	//if the display are paused
	private boolean editMode = false;	//to be able to edit 
	//private boolean rTDM = false;	// RTDM = Ready to display message, for warning window for Alarm & countdown
	
	
	private int accSavedTime = 0;	//time in seconds saved from earlier runs.
	private int accStartTime = 0;	//start time in seconds.
	private int accBackupTime = 0;	//backup time in seconds value for control.
	
	private int countdownTime = 0;	//time in seconds remaining.
	private int alarmTime = 0;	//time in seconds.
	
	
	public DisplayPanel(){
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		buttonPanel.setLayout(new GridBagLayout());
		setSize(50, 406);
		
		DisplayLabel.setText("Display");
		DisplayTextfield.setText("00:00:00");
		DisplayTextfield.setEditable(false);
		DisplayTextfield.setFont(new Font("SansSerif ", Font.BOLD, 46));
		DisplayLabel.setSize(200, 50);
		DisplayTextfield.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);	//Sets the text in the center of the textfield
		
		//Trick to set the size of the component.
		startButton.setMinimumSize(new Dimension(67,30));
		startButton.setMaximumSize(startButton.getMinimumSize());
		startButton.setPreferredSize(startButton.getMinimumSize());
		resetButton.setMinimumSize(startButton.getMinimumSize());
		resetButton.setMaximumSize(startButton.getMinimumSize());
		resetButton.setPreferredSize(startButton.getMinimumSize());
		editButton.setMinimumSize(startButton.getMinimumSize());
		editButton.setMaximumSize(startButton.getMinimumSize());
		editButton.setPreferredSize(startButton.getMinimumSize());
		
		DisplayTextfield.setMinimumSize(new Dimension(201,60));
		DisplayTextfield.setMaximumSize(DisplayTextfield.getMinimumSize());
		DisplayTextfield.setPreferredSize(DisplayTextfield.getMinimumSize());
		
		DisplayLabel.setMinimumSize(new Dimension(100,25));
		DisplayLabel.setMaximumSize(DisplayLabel.getMinimumSize());
		DisplayLabel.setPreferredSize(DisplayLabel.getMinimumSize());
		
		modeBox.setMinimumSize(new Dimension(100,25));
		modeBox.setMaximumSize(modeBox.getMinimumSize());
		modeBox.setPreferredSize(modeBox.getMinimumSize());
		
		modeBox.setEditable(false);
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(modeBox, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		add(DisplayLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		add(DisplayTextfield, c);
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(startButton, c);
		c.gridx = 1;
		c.gridy = 0;
		buttonPanel.add(resetButton, c);
		c.gridx = 2;
		c.gridy = 0;
		buttonPanel.add(editButton, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		add(buttonPanel, c);
		
		
		ButtonListener bListener = new ButtonListener();
		startButton.addActionListener(bListener);
		resetButton.addActionListener(bListener);
		editButton.addActionListener(bListener);
		modeBox.addActionListener(bListener);
	}
	
	
	public void update(){
		if(mode == 1){	//update clock if more are 1
			DisplayTextfield.setText(TimeManager.getClock());
		} else if(mode == 2 && !editMode){	//accumulator
			if(!paused){
				int accEndTime = TimeManager.getTime();	//end time in seconds.
				if(((accEndTime - accStartTime) + accSavedTime) < accBackupTime){	//if for some reason time goes back....Time Less than BackupTime
					accSavedTime = accBackupTime + 1;	//set SavedTime to BackupTime + 1
					pause();	//pauses the program
					startPause();	//restarts the program
				} else{
					accBackupTime = ((accEndTime - accStartTime) + accSavedTime);
				}
			}
			DisplayTextfield.setText(TimeManager.formatTime(accBackupTime));
		} else if(mode == 3 && !editMode){	//countdown
			if(!paused){
				if(countdownTime > 1){	//countdownTime are greater than 1
					countdownTime --;
					
				} else{
					pause();
					if(countdownTime == 1){
						countdownTime --;
						DisplayTextfield.setText(TimeManager.formatTime(countdownTime));
					}
					//INSERT COUNTDOWN SIGNAL/MESSAGE
					JOptionPane.showMessageDialog(null, "The Time Is Out!");
				}
			}
			DisplayTextfield.setText(TimeManager.formatTime(countdownTime));
		} else if(mode == 4 && !editMode){	//alarm
			if(!paused){
				if(TimeManager.getTime() >= alarmTime && TimeManager.getTime() <= (alarmTime+2)){	//Time greater or equal to alarm AND Time less or equal to Alarm+2
					pause();
					//INSERT ALARM SIGNAL/MESSAGE
					JOptionPane.showMessageDialog(null, "The Alarm Time Is Now!");
				}
			}
			DisplayTextfield.setText(TimeManager.formatTime(alarmTime));
		}
		
	}
	
	public void startPause(){
		if(paused && mode == 2){	//if it is paused, it means START
			accStartTime = TimeManager.getTime();
		} else if(!paused && mode == 2){	//if it is not paused, it means PAUSE
			int accEndTime = TimeManager.getTime();	//end time in seconds.
			accSavedTime += (accEndTime - accStartTime);	//saveTime
			if(accSavedTime > accBackupTime){
				accBackupTime = accSavedTime;	
			}
		}
		paused = !paused;
		startButton.setEnabled(true);
		resetButton.setEnabled(paused);
		editButton.setEnabled(paused);
		
		if(paused){	//if it is paused
			startButton.setText("Start");
		}else{
			startButton.setText("Stop");
		}
	}
	
	public void pause(){
		paused = true;
		startButton.setText("Start");
		startButton.setEnabled(true);
		resetButton.setEnabled(paused);
		editButton.setEnabled(paused);
	}
	
	public boolean isPaused(){
		return paused;
	}
	
	public void reset(){
		if(mode == 2){
			//sets time to 0 and pauses it
			accSavedTime = 0;
			accBackupTime = 0;
			pause();
		} else if(mode == 3){
			//sets time to 0 and pauses it
			countdownTime = 0;
			pause();
		} else if(mode == 4){
			//sets time to 0 and pauses it
			alarmTime = 0;
			pause();
		}
	}
	
	public void editTime(){
		if(!editMode){	//when you start editing
			if(mode != 1){	//should not be able to press edit in clock mode but lets do this anyway
				DisplayTextfield.setEditable(true);
				editMode = true;
				startButton.setEnabled(false);
				resetButton.setEnabled(false);
				editButton.setEnabled(true);
			}
		} else if(DisplayTextfield.getText() != null){	//when you are done editing AND it's not null
			if(TimeManager.isTime(DisplayTextfield.getText())){	//checks if it's correct
				editMode = false;
				DisplayTextfield.setEditable(false);
				startButton.setEnabled(true);
				resetButton.setEnabled(true);
				editButton.setEnabled(true);
				String[] timeParts = DisplayTextfield.getText().split(":");
				int temp = 0;
				temp += (Integer.parseInt(timeParts[0])* 3600);
				temp += (Integer.parseInt(timeParts[1])* 60);
				temp += (Integer.parseInt(timeParts[2]));
				if(mode == 2){
					reset();
					accSavedTime = temp;
					accBackupTime = temp;
				} else if(mode == 3){
					countdownTime = temp;
				} else if(mode == 4){
					alarmTime = temp;
				}
			}else{
				DisplayTextfield.setEditable(false);
				DisplayTextfield.setText("00:00:00");
			}
		} else{
			DisplayTextfield.setEditable(false);
			DisplayTextfield.setText("ERROR");
		}
	}
	
	public void setMode(int displayMode){
		mode = displayMode;
		if(mode == CLOCK){
			DisplayLabel.setText("Clock");
			startButton.setEnabled(false);
			resetButton.setEnabled(false);
			editButton.setEnabled(false);
			DisplayTextfield.setText(TimeManager.getClock());
		} else if(mode == ACCUMULATOR){
			DisplayLabel.setText("Accumulator");
			startButton.setEnabled(true);
			resetButton.setEnabled(true);
			editButton.setEnabled(true);
			DisplayTextfield.setText(TimeManager.formatTime(accBackupTime));
		} else if(mode == COUNTDOWN){
			DisplayLabel.setText("Countdown");
			startButton.setEnabled(true);
			resetButton.setEnabled(true);
			editButton.setEnabled(true);
			DisplayTextfield.setText(TimeManager.formatTime(countdownTime));
		} else if(mode == ALARM) {
			DisplayLabel.setText("Alarm");
			startButton.setEnabled(true);
			resetButton.setEnabled(true);
			editButton.setEnabled(true);
			DisplayTextfield.setText(TimeManager.formatTime(alarmTime));
		}
		modeBox.setSelectedIndex(mode-1);
	}
	
	public int getMode(){
		return mode;
	}
	
	public void setLabel(String text){	//will remove later
		DisplayLabel.setText(text);
	}
	
	public void setTimeText(String text){	//will remove later
		DisplayTextfield.setText(text);
	}
	
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == startButton){
				startPause();
			}else if(e.getSource() == resetButton){
				reset();
			}else if(e.getSource() == editButton){
				editTime();
			}else if(e.getSource() == modeBox){
				if(modeBox.getSelectedIndex() >= 0){
					if(!paused){	//if the display is not paused, pause it to prevent glitches
						startPause();
					}
					if(editMode){	//if edit mode are on turn it off
						editTime();
					}
					setMode(modeBox.getSelectedIndex()+1);
				}
			}
		}
	}
}
