package alcardianTimer;

import java.io.*;
import java.util.*;

public class FileManager {
	private String endOfLine;
	
	
	public FileManager(){
		endOfLine = System.getProperty("line.separator");	//Get end of line symbol for the system this program are running on.
	}
	
	
	public ArrayList<String> ReadFile(String fileName){
		ArrayList<String> buffer = new ArrayList<String>();
		Scanner sc;	//scanner, for read from file
		
		try {
			sc = new Scanner(new File(fileName));
			while (sc.hasNext()){
				buffer.add(sc.nextLine());
			}
			sc.close();
		} catch(FileNotFoundException e){
			System.out.println("ERROR! Can't find file!");
			System.out.println("Trying to create new");
			ArrayList<String> temp = new ArrayList<String>();
			temp.add("4@1#2#3#4");
			System.out.println(fileName);
			writeToFile(temp, fileName);
			return temp;
		}
		return buffer;
	}
	
	public void writeToFile(ArrayList<String> fileLines, String fileName){
		FileWriter writer;	//FileWriter to write to file 
		try {
			writer = new FileWriter(new File(fileName));	//open file named fileName
			int a = 0;	//for the while loop
			
			while (a<fileLines.size()){	//write string from fileLines at position "a" and add \n
				writer.write(fileLines.get(a));
				writer.write(endOfLine);	//write end of line symbol
				a++;
			}
			writer.close();
		}
		catch(IOException e){
			System.out.println("ERROR! IOException while writing to file!");
		}
	}
}
