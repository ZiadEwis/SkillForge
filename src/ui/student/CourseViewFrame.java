package ui.student;

import services.StudentService;
import database.JsonDatabaseManager;
import models.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CourseViewFrame extends JFrame {
    private String studentId, courseId;
    private StudentService ss = new StudentService();
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list = new JList<>(model);

    public CourseViewFrame(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;

        setTitle("Course: " + courseId);
        setSize(700,500);

        loadLessons();

        JButton mark = new JButton("Mark Completed");
        mark.addActionListener(e -> onMark());

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(mark, BorderLayout.SOUTH);
    }

    private void loadLessons() {
        model.clear();
        JSONArray data = JsonDatabaseManager.getInstance().readAll();
        List<String> done = ss.getCompletedLessons(studentId, courseId);

        for (Object o : data) {
            JSONObject obj = (JSONObject) o;
            if ("lesson".equals(obj.get("type")) && courseId.equals(obj.get("courseId"))) {
                String lid = (String) obj.get("id");
                String title = (String) obj.get("title");
                boolean isDone = done.contains(lid);
                model.addElement(lid + " - " + (isDone ? "[Done] " : "") + title);
            }
        }
    }

    private void onMark() {
        String sel = list.getSelectedValue();
        if (sel == null) return;

        String lid = sel.split(" - ")[0];
        boolean ok = ss.markLesson(studentId, courseId, lid);

        JOptionPane.showMessageDialog(this, ok ? "Marked" : "Already completed");
        loadLessons();
    }
}