package test.ui;

import test.database.JsonDatabaseManager;
import test.model.User;
import test.service.AuthenticationService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private AuthenticationService authService;
    private JsonDatabaseManager dbManager;
    
    public LoginFrame(){
        this.dbManager=new JsonDatabaseManager();
        this.authService=new AuthenticationService(dbManager);
        initializeUI();
    }
    private void initializeUI(){
        setTitle("Course Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel titlePanel=new JPanel();
        titlePanel.add(new JLabel("Login", SwingConstants.CENTER));
        titlePanel.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel formPanel=new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridx =0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Email:"), gbc);// emial
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);
        gbc.gridx=0;
        gbc.gridy= 1;
        gbc.fill =GridBagConstraints.NONE;
        gbc.weightx =0;
        formPanel.add(new JLabel("Password:"), gbc); // password
        gbc.gridx =1;
        gbc.fill =GridBagConstraints.HORIZONTAL;
        gbc.weightx =1.0;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton=new JButton("Login");
        signupButton = new JButton("Sign Up");
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
 
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            handleLogin();
            }
        });
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            openSignupFrame();
            }
        });
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            handleLogin();
            }
        });
    }
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }  
        try {
            User user=authService.login(email, password);
            String role=user.getRole();
            dispose();
            if ("STUDENT".equals(role)){
                new StudentDashboardFrame(authService,dbManager).setVisible(true);
            } else if ("INSTRUCTOR".equals(role)) {
                new InstructorDashboardFrame(authService,dbManager).setVisible(true);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,e.getMessage(), 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void openSignupFrame() {
        SignupFrame signupFrame=new SignupFrame(authService,dbManager);
        signupFrame.setVisible(true);
    }
    public AuthenticationService getAuthService() {
        return authService;
    }
    public JsonDatabaseManager getDbManager() {
        return dbManager;
    }
}
