package ui.instructor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import models.Course;
import models.Lesson;
import services.InstructorService;
import ui.components.RoundedButton;
import utils.IdGenerator; 

public class LessonEditorFrame extends JFrame {

    private Course course;
    private InstructorService instructorService;
    
    private JList<String> lessonList; 
    private DefaultListModel<String> listModel;
    private JTextArea contentArea;
    private JTextField titleField;
    private JTextField lessonIdField; 
    private JButton saveLessonButton;
    private JButton newLessonButton;
    private JButton deleteLessonButton;

    public LessonEditorFrame(Course course, InstructorService service) {
        this.course = course;
        this.instructorService = service;
        
        setTitle("Manage Lessons for: " + course.getTitle());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        loadLessonList();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Lesson List Panel
        listModel = new DefaultListModel<>();
        lessonList = new JList<>(listModel);
        lessonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lessonList.addListSelectionListener(e -> displaySelectedLesson());
        
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Lessons"));
        listPanel.add(new JScrollPane(lessonList), BorderLayout.CENTER);
        
        JPanel listButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newLessonButton = new RoundedButton("New Lesson");
        newLessonButton.addActionListener(e -> clearEditor());
        listButtons.add(newLessonButton);
        
        deleteLessonButton = new RoundedButton("Delete Selected");
        deleteLessonButton.addActionListener(e -> deleteLessonAction());
        listButtons.add(deleteLessonButton);
        listPanel.add(listButtons, BorderLayout.SOUTH);
        
        splitPane.setLeftComponent(listPanel);

        // Lesson Editor Panel
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorPanel.setBorder(BorderFactory.createTitledBorder("Lesson Details"));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        lessonIdField = new JTextField(20);
        lessonIdField.setEditable(false);
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; inputPanel.add(lessonIdField, gbc);

        titleField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; inputPanel.add(titleField, gbc);

        editorPanel.add(inputPanel, BorderLayout.NORTH);
        
        contentArea = new JTextArea();
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        editorPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        saveLessonButton = new RoundedButton("Save Lesson");
        saveLessonButton.addActionListener(e -> saveLessonAction());
        editorPanel.add(saveLessonButton, BorderLayout.SOUTH);

        splitPane.setRightComponent(editorPanel);
        splitPane.setDividerLocation(300);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Back Button
        JButton backButton = new RoundedButton("Back to Course Editor");
        backButton.addActionListener(e -> goBackToCourseEditor());
        add(backButton, BorderLayout.SOUTH);
    }

    private void loadLessonList() {
        listModel.clear();
        Course currentCourseData = instructorService.dbManager.getCourseById(course.getCourseId());
        if (currentCourseData != null) {
            course = currentCourseData;
        }

        for (Lesson lesson : course.getLessons()) {
            listModel.addElement(lesson.getTitle() + " (" + lesson.getLessonId() + ")");
        }
    }

    private void displaySelectedLesson() {
        int index = lessonList.getSelectedIndex();
        if (index != -1) {
            Lesson selectedLesson = course.getLessons().get(index);
            lessonIdField.setText(selectedLesson.getLessonId());
            titleField.setText(selectedLesson.getTitle());
            contentArea.setText(selectedLesson.getContent());
        }
    }
    
    private void clearEditor() {
        lessonList.clearSelection();
        lessonIdField.setText("");
        titleField.setText("");
        contentArea.setText("");
    }

    private void saveLessonAction() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        String lessonId = lessonIdField.getText().trim();
        
        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and Content are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Lesson lesson;
        boolean success;

        if (lessonId.isEmpty()) {
            String newLessonId = IdGenerator.generateUniqueId("L");
            lesson = new Lesson(newLessonId, title, content);
            success = instructorService.addLessonToCourse(course.getCourseId(), lesson);
        } else {
            lesson = new Lesson(lessonId, title, content);
            success = instructorService.editLessonInCourse(course.getCourseId(), lesson);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Lesson saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadLessonList(); 
        } else {
            JOptionPane.showMessageDialog(this, "Operation failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        clearEditor();
    }

    private void deleteLessonAction() {
        String lessonId = lessonIdField.getText().trim();
        
        if (lessonId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a lesson to delete.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this lesson?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = instructorService.deleteLessonFromCourse(course.getCourseId(), lessonId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Lesson deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadLessonList();
                clearEditor();
            } else {
                JOptionPane.showMessageDialog(this, "Deletion failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void goBackToCourseEditor() {
        this.dispose();
    }
}