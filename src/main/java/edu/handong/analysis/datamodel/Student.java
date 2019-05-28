package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
   private String studentId;
   private ArrayList<Course> coursesTaken;
   private HashMap<String,Integer> semestersByYearAndSemester;
   
   public Student(String studentId) {
      this.setStudentId(studentId);
   }
   public void addCourse(Course newRecord) {
      if(coursesTaken == null)
         coursesTaken = new ArrayList<Course>();
      coursesTaken.add(newRecord);
   }
   public HashMap<String,Integer> getSemestersByYearAndSemester(){
      int semesterCount = 1;
      for(int i=0;i<coursesTaken.size(); i++) {
         StringBuilder key = new StringBuilder();
         key.append(coursesTaken.get(i).getYearTaken() + "-" + coursesTaken.get(i).getSemesterCourseTaken());
         if(semestersByYearAndSemester == null) semestersByYearAndSemester = new HashMap<String,Integer>();
         if(!semestersByYearAndSemester.containsKey(key.toString())) {
            semestersByYearAndSemester.put(key.toString(), semesterCount);
            semesterCount++;
         }
      }
      return semestersByYearAndSemester;
   }
   public int getNumCourseInNthSemester(int semester) {
      int numCourseInNthSemester = 0;
      for(int i=0;i<coursesTaken.size();i++) {
         StringBuilder key = new StringBuilder();
         key.append(coursesTaken.get(i).getYearTaken() + "-" + coursesTaken.get(i).getSemesterCourseTaken());
         if(semestersByYearAndSemester.get(key.toString()) == semester) {
            numCourseInNthSemester++;
         }
      }
      
   return numCourseInNthSemester;
   }
public String getStudentId() {
	return studentId;
}
public void setStudentId(String studentId) {
	this.studentId = studentId;
}

}