package org.alcardian.timer.timelog;

public class TimeLog {
	private String date;	//Format: YYYY-MM-DD.
	private int time;	//time in seconds.
	private int mode;	//What mode the display setting the value were in.
	
	/**
	 * 
	 * @param date A String in the format: YYYY-MM-DD.
	 * @param time A integer containing time in seconds.
	 * @param mode A integer containing a value that represents what kind of time log it is.
	 */
	public TimeLog(String date, int time, int mode){
		this.date = date;
		this.time = time;
		this.mode = mode;
	}
	
	/**
	 * Splits the String into sub Strings and gets out Date, Time & Mode from it.
	 * @param timeLogString A String in the format: Date#Time#Mode
	 */
	public TimeLog(String timeLogString){
		String[] sp = timeLogString.split("#");
		if(sp.length == 3){
			this.date = sp[0];
			this.time = Integer.parseInt(sp[1]);
			this.mode = Integer.parseInt(sp[2]);
		}
	}
	
	/**
	 * 
	 * @return a String in the format date#time#mode
	 */
	public String TimeLogToString(){
		return (date + "#" + time + "#" + mode);
	}
	
	public String getDate(){
		return date;
	}
	
	public int getTime(){
		return time;
	}
	
	public int getMode(){
		return mode;
	}

}
