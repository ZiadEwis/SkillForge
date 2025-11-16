/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author ERR0R
 */
public class Course {
    private String title;
    private String description;
    private int courseID;
    private int instructorId;
    private List<Integer> students = new ArrayList<>(); //IDs
    private List<Lesson> lessons = new ArrayList<>();
    //setters
    public void setTitle(String title){
        this.title=title;
    }
    public void setCourseID(int courseID){
        this.courseID=courseID;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setInstructorId(int instructorId){
        this.instructorId=instructorId;
    }
    public void setLessons(List<Lesson> lessons){
        this.lessons=lessons;
    }
    public void setStudents(List<Integer> students){
        this.students=students;
    }
   //getters
    public String getTitle(){
       return title;
    }
    public int getCourseID(){
        return courseID;
    }
    public List<Lesson> getLessons(){
        return lessons;
    }
    public List<Integer> getStudents(){
        return students;
    }
    public String getDescription(){
        return description;
    }
    public int getInstructorId(){
        return instructorId;
    }
    //methods
    public void addLesson(Lesson lesson){ lessons.add(lesson);}
    public void removeLesson(int lessonID) {lessons.remove(lessonID);}
    public void editLesson(int lessonID,Lesson newLesson){lessons.set(lessonID,newLesson);}
    public void enrollStudent(int studentID) {
        if (!students.contains(studentID))
            students.add(studentID);
    }
    public void unenrollStudent(int studentID) {
        students.remove(studentID);
    }
}

