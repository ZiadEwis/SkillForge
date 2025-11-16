package services;

import database.JsonDatabaseManager;
import models.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.*;
public class StudentService {
    private JsonDatabaseManager db = JsonDatabaseManager.getInstance();

    public List<Course> browseCourses() {
        JSONArray data = db.readAll();
        List<Course> out = new ArrayList<>();

        for (Object o : data) {
            JSONObject obj = (JSONObject) o;
            if ("course".equals(obj.get("type"))) {
                Course c = new Course(
                        (String) obj.get("id"),
                        (String) obj.get("instructorId"),
                        (String) obj.get("title"),
                        (String) obj.get("description")
                );
                out.add(c);
            }
        }
        return out;
    }

    public boolean enroll(String studentId, String courseId) {
        JSONArray data = db.readAll();

        for (Object o : data) {
            JSONObject obj = (JSONObject) o;
            if ("enrollment".equals(obj.get("type")) &&
                    studentId.equals(obj.get("studentId")) &&
                    courseId.equals(obj.get("courseId"))) {
                return false; // already enrolled
            }
        }

        JSONObject newEnroll = new JSONObject();
        newEnroll.put("id", "enroll_" + UUID.randomUUID());
        newEnroll.put("type", "enrollment");
        newEnroll.put("studentId", studentId);
        newEnroll.put("courseId", courseId);

        db.addObject(newEnroll);
        return true;
    }

    public boolean markLesson(String studentId, String courseId, String lessonId) {
        JSONArray data = db.readAll();

        for (Object o : data) {
            JSONObject obj = (JSONObject) o;
            if ("progress".equals(obj.get("type")) &&
                    studentId.equals(obj.get("studentId")) &&
                    courseId.equals(obj.get("courseId")) &&
                    lessonId.equals(obj.get("lessonId"))) {
                return false; // already done
            }
        }

        JSONObject p = new JSONObject();
        p.put("id", "progress_" + UUID.randomUUID());
        p.put("type", "progress");
        p.put("studentId", studentId);
        p.put("courseId", courseId);
        p.put("lessonId", lessonId);

        db.addObject(p);
        return true;
    }

    public List<String> getCompletedLessons(String studentId, String courseId) {
        JSONArray data = db.readAll();
        List<String> out = new ArrayList<>();

        for (Object o : data) {
            JSONObject obj = (JSONObject) o;
            if ("progress".equals(obj.get("type")) &&
                    studentId.equals(obj.get("studentId")) &&
                    courseId.equals(obj.get("courseId"))) {
                out.add((String) obj.get("lessonId"));
            }
        }
        return out;
    }
}