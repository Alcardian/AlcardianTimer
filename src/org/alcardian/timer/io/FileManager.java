package org.alcardian.timer.io;

import java.io.*;
import java.util.*;

public class FileManager {
	
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
			e.printStackTrace();
		}
		return buffer;
	}
	
	public void writeToFile(ArrayList<String> fileLines, String fileName){
		FileWriter writer;	//FileWriter to write to file 
		try {
			writer = new FileWriter(new File(fileName));	//open file at path named fileName
			int a = 0;	//for the while loop
			
			while (a<fileLines.size()){	//write string from fileLines at position "a" and add \n
				writer.write(fileLines.get(a));
				writer.write("\n");	//write end of line symbol
				a++;
			}
			writer.close();
		}
		catch(IOException e){
			System.out.println("ERROR! IOException while writing to file!");
		}
	}
}