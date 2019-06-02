package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysise.utils.NotEnoughArgumentException;
import edu.handong.analysise.utils.Utils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;



public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	private String inputPath;
	private String outputPath;
	private String analysisOption;
	private String courseCodeInput;
	private int startYear;
	private int endYear;
	private boolean help;

	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		Options options = createOptions();
		if(parseOptions(options, args)){	
			if (help){
				printHelp(options);
				return;
			}
			if(analysisOption.equals("2"))
			{
				if(courseCodeInput==null) 
				{
					System.out.println("Put the CourseCode!");
					printHelp(options);
					System.exit(0);
				}
			}
		
		ArrayList<String> lines = Utils.getLines(inputPath, true);
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		if(analysisOption.equals("1")) {
			
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, outputPath);
	}
		else {
			ArrayList<String> linesToBeSaved = new ArrayList<String>();
			String courseName = null;
			String rate;
			String courseAdd;
			float totalStuNum;
			float takeNum;
			
			for(int year = startYear; year <= endYear; year++)
			{
				for(int semesterCourseTaken = 1; semesterCourseTaken<=4; semesterCourseTaken++)
				{
					totalStuNum = 0;
					takeNum = 0;
					for(String key : students.keySet())
					{
						Student stu = students.get(key);
						ArrayList<Course> coursesTaken = stu.getCoursesTaken();
						int i = 0;
						for(Course course : coursesTaken)
						{
							if(course.getSemesterCourseTaken()==semesterCourseTaken&&course.getYearTaken()==year)
							{
								i++;
								if(course.getCourseCode().equals(courseCodeInput))
								{
									takeNum ++;
									courseName = course.getCourseName();
								}
							}
						}
						if(i!=0) totalStuNum ++;
					}
					if(takeNum == 0) 
						continue;
					int total = 0;
					rate = String.format("%.1f%%",takeNum/totalStuNum*100);
					courseAdd = year + "," + semesterCourseTaken + "," + courseCodeInput + "," + courseName + ","+ (int)totalStuNum + "," + (int)takeNum + "," + rate;
					total += takeNum;
					linesToBeSaved.add(courseAdd);
				}
			}
			Utils.writeAFile2(linesToBeSaved, outputPath);
		}
	}
}

	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		
		// TODO: Implement this method
		HashMap<String,Student> stuinfo = new HashMap<String,Student>();
		
		for(int i = 0; i<lines.size();i++) {
			Course cour =  new Course(lines.get(i));
			if(!stuinfo.containsKey(cour.getStudentId())) {
				Student newStudent = new Student(cour.getStudentId());
				stuinfo.put(cour.getStudentId(), newStudent);
			}
			stuinfo.get(cour.getStudentId()).addCourse(cour);
				
		}
		
		return stuinfo; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. 
	 * The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method
		ArrayList<String> result = new ArrayList<String>();
		
		for(String key : sortedStudents.keySet()) {
			int total = sortedStudents.get(key).getSemestersByYearAndSemester().size();
			for(int i = 1; i<= total; i++) {
				result.add(key+","+total+","+Integer.toString(i)+","+Integer.toString(sortedStudents.get(key).getNumCourseInNthSemester(i)));
			}
		}
		

		
		return result; // do not forget to return a proper variable.
	}


private boolean parseOptions(Options options, String[] args) {
	CommandLineParser parser = new DefaultParser();

	try {

		CommandLine cmd = parser.parse(options, args);
		
		inputPath = cmd.getOptionValue("i");
		outputPath = cmd.getOptionValue("o");
		analysisOption = cmd.getOptionValue("a");
		courseCodeInput = cmd.getOptionValue("c");
		startYear = Integer.parseInt(cmd.getOptionValue("s"));
		endYear = Integer.parseInt(cmd.getOptionValue("e"));
		help = cmd.hasOption("h");

	} catch (Exception e) {
		printHelp(options);
		return false;
	}
return true;
}


//The Opthion method
private Options createOptions() {
	Options options = new Options();

	//Option input "i"
	options.addOption(Option.builder("i").longOpt("input").required().hasArg().argName("Input path").desc("Set an input file path").build());
	
	//Option output "o"
	options.addOption(Option.builder("o").longOpt("output").required().hasArg().argName("Output path").desc("Set an output file path").build());

	//Option analysis "a"
	options.addOption(Option.builder("a").longOpt("analysis").required().hasArg().argName("Analysis option").desc("1: Count courses per semester, 2: Count per course name and year").build());
	
	//Option coursecode "c"
	options.addOption(Option.builder("c").longOpt("coursecode").hasArg().argName("course code").desc("Course code for '-a 2' option").build());
	
	//Option startyear "s"
	options.addOption(Option.builder("s").longOpt("startyear").required().hasArg().argName("Start year for analysis").desc("Set the start year for analysis e.g., -s 2002").build());
	
	//Option endyear "e"
	options.addOption(Option.builder("e").longOpt("endyear").required().hasArg().argName("End year for analysis").desc("Set the end year for analysis e.g., -s 2005").build());
	
	//Option help "h'
	options.addOption(Option.builder("h").longOpt("help").argName("Help").desc("Show a Help page").build());

	return options;
}

private void printHelp(Options options) {
	// automatically generate the help statement
	HelpFormatter formatter = new HelpFormatter();
	String header = "HGU Course Analyzer";
	String footer ="";
	formatter.printHelp("HGUCourseCounter", header, options, footer, true);
}
}
