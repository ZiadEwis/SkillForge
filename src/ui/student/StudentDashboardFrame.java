package ui.student;

import services.StudentService;
import database.JsonDatabaseManager;
import models.Course;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentDashboardFrame extends JFrame {
    private StudentService ss = new StudentService();
    private String studentId;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> list = new JList<>(listModel);

    public StudentDashboardFrame(String studentId) {
        this.studentId = studentId;
        setTitle("Student Dashboard");
        setSize(700,500);

        loadCourses();

        JButton enroll = new JButton("Enroll");
        enroll.addActionListener(e -> onEnroll());

        JButton open = new JButton("Open Course");
        open.addActionListener(e -> onOpen());

        JPanel right = new JPanel(new GridLayout(2,1));
        right.add(enroll);
        right.add(open);

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
    }

    private void loadCourses() {
        listModel.clear();
        List<Course> courses = ss.browseCourses();
        for (Course c : courses) listModel.addElement(c.getId() + " - " + c.getTitle());
    }

    private String sel() {
        String x = list.getSelectedValue();
        return (x == null ? null : x.split(" - ")[0]);
    }

    private void onEnroll() {
        String cid = sel();
        if (cid == null) return;
        boolean ok = ss.enroll(studentId, cid);
        JOptionPane.showMessageDialog(this, ok ? "Enrolled" : "Already enrolled");
    }

    private void onOpen() {
        String cid = sel();
        if (cid == null) return;
        new CourseViewFrame(studentId, cid).setVisible(true);
    }
}