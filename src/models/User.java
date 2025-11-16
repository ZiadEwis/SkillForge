/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ERR0R
 */
public class User {
    private int userID;
    private String name;
    private String passwordHash;
    private String email;
    //getters
    public String getName(){ return name;}
    public String getEmail(){ return email;}
    public String getPasswordHash(){return passwordHash;}
    public int getUserID(){ return userID;}
    //setters
    public void setName(String name){ this.name=name;}
    public void setEmail(String email){ this.email=email;}
    public void setPasswordHash(String passwordHash){ this.passwordHash=passwordHash;}
    public void setUserID(int userID){ this.userID=userID;}
}
