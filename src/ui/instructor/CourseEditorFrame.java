package ui.instructor;

import javax.swing.*;
import java.awt.*;
import models.Instructor;
import services.InstructorService;
import models.Course;
import ui.components.RoundedButton; 

public class CourseEditorFrame extends JFrame {

    private Instructor currentInstructor;
    private InstructorService instructorService;
    private Course courseToEdit; 

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton saveButton;
    private JButton lessonManagementButton;
    private JButton deleteButton;

    public CourseEditorFrame(Instructor instructor, InstructorService service, Course course) {
        this.currentInstructor = instructor;
        this.instructorService = service;
        this.courseToEdit = course;
        
        setTitle(course == null ? "Create New Course" : "Edit Course: " + course.getTitle());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLayout(new BorderLayout());

        initComponents();
        loadCourseData(); 
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        formPanel.add(new JLabel("Course Title:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Course Description:"));
        descriptionArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(scrollPane);

        add(formPanel, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        saveButton = new RoundedButton("Save Course");
        saveButton.addActionListener(e -> saveCourseAction());
        controlsPanel.add(saveButton);
        
        lessonManagementButton = new RoundedButton("Manage Lessons");
        lessonManagementButton.setEnabled(courseToEdit != null);
        lessonManagementButton.addActionListener(e -> openLessonEditor());
        controlsPanel.add(lessonManagementButton);
        
        deleteButton = new RoundedButton("Delete Course");
        deleteButton.setBackground(Color.RED);
        deleteButton.setEnabled(courseToEdit != null);
        deleteButton.addActionListener(e -> deleteCourseAction());
        controlsPanel.add(deleteButton);
        
        JButton backButton = new RoundedButton("Back to Dashboard");
        backButton.addActionListener(e -> goBackToDashboard());
        controlsPanel.add(backButton);

        add(controlsPanel, BorderLayout.SOUTH);
    }

    private void loadCourseData() {
        if (courseToEdit != null) {
            titleField.setText(courseToEdit.getTitle());
            descriptionArea.setText(courseToEdit.getDescription());
        }
    }

    private void saveCourseAction() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        
        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and Description are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Course result;
        if (courseToEdit == null) {
            result = instructorService.createCourse(currentInstructor.getUserId(), title, description);
            if (result != null) {
                JOptionPane.showMessageDialog(this, "Course created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            result = instructorService.editCourse(courseToEdit.getCourseId(), title, description);
            if (result != null) {
                JOptionPane.showMessageDialog(this, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        if (result != null) {
            goBackToDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Operation failed. Check logs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCourseAction() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this course?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = instructorService.deleteCourse(courseToEdit.getCourseId(), currentInstructor.getUserId());
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Course deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                goBackToDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Deletion failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openLessonEditor() {
        if (courseToEdit != null) {
            this.dispose();
            new LessonEditorFrame(courseToEdit, instructorService);
        }
    }
    
    private void goBackToDashboard() {
        this.dispose();
        new InstructorDashboardFrame(currentInstructor, instructorService);
    }
}