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
public class Student extends User {
    private List<Integer> enrolledCourses = new ArrayList<>();
    private List<Integer> completedLessons = new ArrayList<>(); //Track progress
    //setters and getters
    public List<Integer> getEnrolledCourses(){
        return enrolledCourses;
    }
    public List<Integer> getCompletedLessons(){
        return completedLessons;
    }
    public void setEnrolledCourses(List<Integer> enrolledCourses){
        this.enrolledCourses=enrolledCourses;
    }
    public void setCompletedLessons(List<Integer> completedLessons){
        this.completedLessons=completedLessons;
    }
    //methods
    public void enrollCourse(int courseId){
    if (!enrolledCourses.contains(courseId)){
        enrolledCourses.add(courseId);
        }
    }
    public void completeLesson(int lessonId) {
     if (!completedLessons.contains(lessonId)) {
        completedLessons.add(lessonId);
    }
}   
}
