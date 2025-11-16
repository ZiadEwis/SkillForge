package services;

import models.Course;
import models.Instructor;
import models.Lesson;
import database.JsonDatabaseManager;
import utils.IdGenerator;
import java.util.List;
import java.util.stream.Collectors;

public class InstructorService {

    private final JsonDatabaseManager dbManager; 
    
    public InstructorService(JsonDatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

  
    // Course Management (Backend)
   

    public Course createCourse(String instructorId, String title, String description) {
        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()) {
            return null; 
        }

        String courseId = IdGenerator.generateUniqueId("C"); 

        Course newCourse = new Course(courseId, title, description, instructorId);

        try {
            dbManager.saveCourse(newCourse); 

            Instructor instructor = dbManager.getInstructorById(instructorId); 
            
            if (instructor != null) {
                instructor.addCreatedCourse(courseId);
                dbManager.updateUser(instructor); 
            } else {
                System.err.println("Error: Instructor with ID " + instructorId + " not found. Course saved but instructor link missing.");
            }

            return newCourse;
        } catch (Exception e) {
            System.err.println("Failed to create course or update instructor data: " + e.getMessage());
            return null;
        }
    }

    public Course editCourse(String courseId, String newTitle, String newDescription) {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            return null; 
        }

        try {
            Course courseToUpdate = dbManager.getCourseById(courseId);

            if (courseToUpdate == null) {
                return null;
            }

            courseToUpdate.setTitle(newTitle);
            courseToUpdate.setDescription(newDescription);

            dbManager.updateCourse(courseToUpdate); 

            return courseToUpdate;
        } catch (Exception e) {
            System.err.println("Failed to edit course: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteCourse(String courseId, String instructorId) {
        try {
            boolean deleted = dbManager.deleteCourse(courseId);

            if (deleted) {
                Instructor instructor = dbManager.getInstructorById(instructorId);
                if (instructor != null) {
                    instructor.getCreatedCourses().remove(courseId);
                    dbManager.updateUser(instructor);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Failed to delete course: " + e.getMessage());
            return false;
        }
    }

    public List<Course> getCoursesByInstructorId(String instructorId) {
        try {
            Instructor instructor = dbManager.getInstructorById(instructorId);
            if (instructor == null) {
                return List.of(); 
            }
            List<String> createdIds = instructor.getCreatedCourses();
            
            return dbManager.getAllCourses().stream()
                .filter(c -> createdIds.contains(c.getCourseId()))
                .collect(Collectors.toList());
            
        } catch (Exception e) {
            System.err.println("Failed to get courses for instructor: " + e.getMessage());
            return List.of();
        }
    }

    public List<String> getEnrolledStudents(String courseId) {
        try {
            Course course = dbManager.getCourseById(courseId);
            if (course != null) {
                return course.getStudents();
            }
            return List.of();
        } catch (Exception e) {
            System.err.println("Failed to get enrolled students: " + e.getMessage());
            return List.of();
        }
    }

    // Lesson Management 
    
    public boolean addLessonToCourse(String courseId, Lesson lesson) {
        try {
            Course course = dbManager.getCourseById(courseId);
            if (course != null) {
                course.addLesson(lesson);
                dbManager.updateCourse(course);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Failed to add lesson: " + e.getMessage());
            return false;
        }
    }
    
    public boolean editLessonInCourse(String courseId, Lesson updatedLesson) {
        try {
            Course course = dbManager.getCourseById(courseId);
            if (course != null) {
                List<Lesson> lessons = course.getLessons();
                for (int i = 0; i < lessons.size(); i++) {
                    if (lessons.get(i).getLessonId().equals(updatedLesson.getLessonId())) {
                        lessons.set(i, updatedLesson);
                        dbManager.updateCourse(course);
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Failed to edit lesson: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteLessonFromCourse(String courseId, String lessonId) {
        try {
            Course course = dbManager.getCourseById(courseId);
            if (course != null) {
                boolean removed = course.removeLesson(lessonId);
                if (removed) {
                    dbManager.updateCourse(course);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Failed to delete lesson: " + e.getMessage());
            return false;
        }
    }
}