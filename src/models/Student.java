package models;


import java.util.*;


public class Student extends User {
    private List<String> enrolledCourses; // list of courseIds
    private Map<String, Set<String>> progress; // courseId -> set of completed lessonIds


    public Student() {
        super();
        this.enrolledCourses = new ArrayList<>();
        this.progress = new HashMap<>();
        this.role = "Student";
    }


    public Student(String userId, String name, String email, String passwordHash) {
        super(userId, name, email, passwordHash, "Student");
        this.enrolledCourses = new ArrayList<>();
        this.progress = new HashMap<>();
    }

    public Student(String userId, String role, String name, String email, String passwordHash) {
        super(userId, role, name, email, passwordHash);
        this.enrolledCourses = new ArrayList<>();
        this.progress = new HashMap<>();
    }
    // enrollment/progress helpers
    public boolean isEnrolled(String courseId) { return enrolledCourses.contains(courseId); }
    public void enrollCourse(String courseId) {
        if (!isEnrolled(courseId)) enrolledCourses.add(courseId);
        progress.putIfAbsent(courseId, new HashSet<>());
    }
    public void markLessonCompleted(String courseId, String lessonId) {
        progress.putIfAbsent(courseId, new HashSet<>());
        progress.get(courseId).add(lessonId);
    }
    public boolean isLessonCompleted(String courseId, String lessonId) {
        return progress.containsKey(courseId) && progress.get(courseId).contains(lessonId);
    }


    public List<String> getEnrolledCourses() { return enrolledCourses; }
    public void setEnrolledCourses(List<String> enrolledCourses) { this.enrolledCourses = enrolledCourses; }
    public Map<String, Set<String>> getProgress() { return progress; }
    public void setProgress(Map<String, Set<String>> progress) { this.progress = progress; }
}