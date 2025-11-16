package ui.student;

import models.Course;
import models.Lesson;
import models.Student;
import services.LessonService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LessonViewerFrame extends JFrame {

    private LessonService lessonService;
    private Student student;
    private Course course;
    private Lesson lesson;

    private JLabel titleLabel;
    private JTextArea contentArea;
    private JButton completeButton;
    private JButton backButton;

    public LessonViewerFrame(Student student, Course course, Lesson lesson) {
        this.student = student;
        this.course = course;
        this.lesson = lesson;
        this.lessonService = new LessonService();

        setTitle("Lesson Viewer");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel(lesson.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        contentArea = new JTextArea(lesson.getContent());
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        completeButton = new JButton("Mark as Completed");
        backButton = new JButton("Back");

        completeButton.addActionListener(e -> markCompleted());

        backButton.addActionListener(e -> {
            dispose();
        });

        bottomPanel.add(completeButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void markCompleted() {
        try {
            lessonService.markLessonCompleted(student, course.getCourseId(), lesson.getLessonId());
            JOptionPane.showMessageDialog(this,
                    "Lesson marked as completed!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error marking lesson completed.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
