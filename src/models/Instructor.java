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
public class Instructor extends User{
     private List<Integer> createdCourses = new ArrayList<>();
    //setters and getters
    public List<Integer> getCreatedCourses(){
        return createdCourses;
    }
    public void setCreatedCourses(List<Integer> createdCourses){
        this.createdCourses=createdCourses;
    }
    //methods
    public void addCourse(int courseID){
         if (!createdCourses.contains(courseID)){
            createdCourses.add(courseID);
        }
    }
    public void removeCourse(int courseID){
         if (createdCourses.contains(courseID)) {
            createdCourses.remove(courseID);
        }
    }
}
