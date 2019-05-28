package edu.handong.analysis.datamodel;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(String line) {
		String[] lists = line.split(",");
		studentId = lists[0].trim();
		yearMonthGraduated = lists[1].trim();
		firstMajor = lists[2].trim();
		secondMajor = lists[3].trim();
		courseCode = lists[4].trim();
		courseName = lists[5].trim();
		courseCredit = lists[6].trim();
		yearTaken = Integer.parseInt(lists[7].trim());
		semesterCourseTaken = Integer.parseInt(lists[8].trim());
	}
	public String getStudentId() {
		return studentId;
	}
	public String getYearMonthGraduated() {
		return yearMonthGraduated;
	}
	public String getFirstMajor() {
		return firstMajor;
	}
	public String getSecondMajor() {
		return secondMajor;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public String getCourseCredit() {
		return courseCredit;
	}
	public int getYearTaken() {
		return yearTaken;
	}
	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}
}