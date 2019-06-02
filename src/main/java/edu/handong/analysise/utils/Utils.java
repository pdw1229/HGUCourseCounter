package edu.handong.analysise.utils;

import java.util.ArrayList;
import 	java.io.*;
import java.util.Scanner;

public class Utils {
	
	public static ArrayList<String> getLines(String file,boolean removeHeader)
	{
		ArrayList<String> list =new ArrayList<String>();
		try {
			
			Scanner inputStream =new Scanner(new File(file));
			String line;
            while(inputStream.hasNextLine())
            {
            		line=inputStream.nextLine();
            		if(removeHeader==true)
            		{
            			removeHeader=false;
            			continue;
            		}
            		else
            		{
            			list.add(line);
            		}	
            }
            inputStream.close();
		} catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
		}
		return list;
	}
	
	public static void writeAFile(ArrayList<String>lines,String targetFileName)
	{
		PrintWriter outputStream = null;
		
		try {
			outputStream = new PrintWriter(targetFileName);
		} catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		outputStream.println("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		for(String line : lines)
		{
			outputStream.println(line);
		}
	outputStream.close();
	}
	public static void writeAFile2(ArrayList<String>lines,String targetFileName)
	{
		PrintWriter outputStream = null;
		
		try {
			outputStream = new PrintWriter(targetFileName);
		} catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		outputStream.println("Year, Semester, CourseCode, CourseName, TotalStudents, StudentsTaken, Rate");
		for(String line : lines)
		{
			outputStream.println(line);
		}
	outputStream.close();
	}
}