package services;

import database.JsonDatabaseManager;
import models.Course;
import models.Lesson;
import models.Student;

import java.util.ArrayList;
import java.util.List;

public class LessonService {
    private JsonDatabaseManager db;
    
    public LessonService(){
        this.db = JsonDatabaseManager.getInstance();
    }
    
    public List<Lesson> getLessonsByCourseId(String courseId){
        Course course = db.getCourseById(courseId);
        if(course == null) return new ArrayList<>();
        return course.getLessons();
    }
    
    public void addLesson(String courseId, Lesson lesson){
        Course course = db.getCourseById(courseId);
        if(course == null) return;
        
        if(course.getLessons() == null)
            course.setLessons(new ArrayList<>());
        
        course.getLessons().add(lesson);
        db.saveCourses();
    }
    
    public void editLesson(String courseId, Lesson updatedLesson){
        Course course = db.getCourseById(courseId);
        if(course == null) return;
        
        List<Lesson> lessons = course.getLessons();
        if(lessons == null) return;
        
        for(int i=0; i < lessons.size(); i++){
            if(lessons.get(i).getLessonId().equals(updatedLesson.getLessonId())){
                lessons.set(i, updatedLesson);
                db.saveCourses();
                return;
            }
        }
    }
    
    public void deleteLesson(String courseId, String lessonId){
        Course course = db.getCourseById(courseId);
        if (course == null) return;
        
        List<lesson> lessons = course.getLessons();
        if(lessons == null) return;
        
        lessons.removeIf(l -> l.getLessonId().equals(lessonId));
        db.saveCourses();
    }
    
    public void markLessonCompleted(Student student, String courseId, String lessonId){
        if(!student.getProgress().containsKey(courseId)){
            student.getProgress().put(courseId, new ArrayList<>());
        }
        
        List<String> completedLessons = student.getProgress().get(courseId);

        if (!completedLessons.contains(lessonId)) {
            completedLessons.add(lessonId);
        }

        db.updateStudent(student);
    }
}
