package alcardianTimer;

import java.util.Calendar;

public class TimeManager {
	
	
	public static int getTime(){
		int time = 0;	//temp variable to store time in
		Calendar cal = Calendar.getInstance();	//creating a Calendar object & saves the current time
		time += cal.get(Calendar.SECOND);	//gets the time(seconds) added to cal
		time += (cal.get(Calendar.MINUTE) * 60);	//gets the time(minutes converted to seconds) added to cal
		time += (cal.get(Calendar.HOUR_OF_DAY) * 3600);	//gets the time(hours converted to seconds) added to cal.
		return time;
	}
	
	
	public static String formatTime(int T){
		int hours;
        int minutes;
        int seconds;
        
        hours = (T / 3600);
        minutes = ((T % 3600) / 60);
        seconds = (T % 60);
        
        String string = "";
        if(hours < 10)
            string += "0";
        
        string += hours + ":";
        if(minutes < 10)
            string += "0";
        
        string += minutes + ":";
        if(seconds < 10)
            string += "0";
        
        string += seconds;
        return string;
	}
	
	public static boolean isTime(String time){
		/* Removed due to if hours are more than 99
		if(time.length() != 8){
			return false;
		}
		*/
		String[] timeParts = time.split(":");
		if(timeParts.length != 3){
			return false;
		}
		if((!isNumber(timeParts[0], false)) || (!isNumber(timeParts[1], false)) || (!isNumber(timeParts[2], false))){
			return false;
		}
		return true;
	}

	
	public static int timePassed(int startTime, int stopTime){
		return stopTime - startTime;
	}
	
	public static String getClock(){
		int time = getTime();
		return formatTime(time);
	}
	
	public static boolean isNumber(String number, boolean wantDouble){ 
		char temp;
		boolean dotUsed = false;	//if . have already been used in the number
		for(int b=0;b < number.length(); b++){
			temp = number.charAt(b);
			if(!(temp == '0' || temp == '1' || temp == '2' || temp == '3' || temp == '4' || temp == '5' || temp == '6' || temp == '7' || temp == '8' || temp == '9' || temp == '-')){
				if(!(wantDouble && (temp == '.'))){
					return false;  //false if it is not a number
					}
				}
			if(temp == '.'){
				if(!dotUsed){
					dotUsed = true;
				} else{
					return false;	//return false if there are more than one '.'
				}
			}
			}
		return true;
		}
}
