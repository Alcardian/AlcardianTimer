package alcardianTimer;

//import org.alcardian.timer.timelog.TimeLogWindow;

public class AlcardianTimer {
	private static int x = 50;
	private static int y = 50;
	/**
	 * @param args
	 */
	public static void main(String[] args){
		if(args.length > 0){
			System.out.println("Found arguments.");
			System.out.println();
			for(int a = 0; a < args.length; a++){
				System.out.println("Argument: " + args[a]);
				String[] sp = args[a].split("=");
				if(sp.length == 2){
					if (sp[0].equals("Path")) {
						AlcardianTimer_Frame.path = sp[1] + "\\";
						System.out.println("Setting path to: " + AlcardianTimer_Frame.path);
					}else if(sp[0].equals("X")){
						if(isNumeric(sp[1])){
							x = Integer.parseInt(sp[1]);
							System.out.println("Setting X coordinate as: " + Integer.parseInt(sp[1]) + ".");
						}
					}else if(sp[0].equals("Y")){
						if(isNumeric(sp[1])){
							y = Integer.parseInt(sp[1]);
							System.out.println("Setting Y coordinate as: " + Integer.parseInt(sp[1]) + ".");
						}
					}else if(sp[0].equals("Size")){
						if(isNumeric(sp[1])){
							AlcardianTimer_Frame.windowSize = Integer.parseInt(sp[1]);
							System.out.println("Setting window size to: " + AlcardianTimer_Frame.windowSize);
						}
					}else{
						System.out.println("Could not identify argument");
					}
				}else{
					System.out.println("Argument not matching format!");
				}
				System.out.println();
			}
			System.out.println("");
		}
		//TimeLogWindow log = new TimeLogWindow();
		//log.setVisible(true);
		runGUI();
	}

	static void runGUI(){
		AlcardianTimer_Frame alcFrame = new AlcardianTimer_Frame();
		alcFrame.setVisible(true);
		alcFrame.setLocation(x, y); // x,y the windows location on screen
	}
	
	/**
	 * 
	 * @param number
	 * @return If the String you put in is a valid number it returns true. If it not a valid number it returns false.
	 */
	static boolean isNumeric(String number){
		boolean flag = true;
		for(int a=0; a<number.length(); a++){
			if(!Character.isDigit(number.charAt(a))){	//if any char is not a valid digit set flag to false
				if(a == 0 && number.charAt(a) == '-'){	//if char that is not a digit is the first char in string and contains a - sign, do this.
					
				}else{	//Set it to false
					flag = false;
				}
			}
		}
		return flag;
	}
}
