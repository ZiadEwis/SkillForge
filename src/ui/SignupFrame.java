/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/// important note::: I didn't have access to method getNextUserId so I assuned it works fine so you will need to implement it ya Ziaad 
package ui;
/**
 *
 * @author ERR0R
 */
import javax.swing.*;
import java.awt.*;
import backend.database.JsonDatabaseManager;
import backend.models.*;
import backend.utils.*;

public class SignupFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton createButton;
    private JButton backButton;
    private JsonDatabaseManager db;
    public SignupFrame(JsonDatabaseManager db) {
        this.db = db;
        setTitle("Signup");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new GridLayout(4, 2, 10, 10));
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        //the buttons
        createButton = new JButton("Create Account");
        backButton = new JButton("Back");
        add(createButton);
        add(backButton);
        createButton.addActionListener(e -> signup());
        backButton.addActionListener(e -> goBack());
        setVisible(true);
    }
    private void signup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // validationss
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }
        if(!EmailValidator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format!");
            return;
        }
        if(password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!");
            return;
        }
        if(db.findByEmail(email) != null) {
            JOptionPane.showMessageDialog(this, "Email already exists!");
            return;
        }
        // user ceration
        int newUserId = db.getNextUserId();
        Student newStudent = new Student();
        newStudent.setUserID(newUserId);
        newStudent.setName(name);
        newStudent.setEmail(email);
        newStudent.setRole("Student");
        newStudent.setPasswordHash(Hashing.sha256(password));
        db.addUser(newStudent);
        JOptionPane.showMessageDialog(this, "Account created successfully!");

        // goo back to login
        this.dispose();
        new LoginFrame(db).setVisible(true);
    }
    private void goBack() {
        this.dispose();
        new LoginFrame(db).setVisible(true);
    }
}
