/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ui;
/**
 *
 * @author ERR0R
 */
import javax.swing.*;
import java.awt.*;
import backend.database.JsonDatabaseManager;
import backend.models.*;
    public class LoginFrame extends JFrame {
        private JTextField emailField;
        private JPasswordField passwordField;
        private JButton loginButton;
        private JButton signupButton;
        private JsonDatabaseManager db;

    public LoginFrame(JsonDatabaseManager db) {
        this.db = db;
        setTitle("Login");
        setSize(350,180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new GridLayout(3, 2, 10, 10));
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");
        add(loginButton);
        add(signupButton);
        loginButton.addActionListener(e->login());
        signupButton.addActionListener(e->{
            this.dispose();
            new SignupFrame(db).setVisible(true);
        });
        setVisible(true);
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        if(email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter email and password.");
            return;
        }
        User user = db.findByEmail(email);
        if(user == null) {
            JOptionPane.showMessageDialog(this,"User not found.");
            return;
        }
        if(!user.getPasswordHash().equals(Utils.hashSHA256(password))) {
            JOptionPane.showMessageDialog(this, "Incorrect password.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user.getName());
        this.dispose();
        if(user.getRole().equalsIgnoreCase("Student")) {
            new StudentDashboard(user,db).setVisible(true);
        } else if(user.getRole().equalsIgnoreCase("Instructor")) {
            new InstructorDashboard(user,db).setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(this, "Unknown role: " + user.getRole());
        }
    }
}
