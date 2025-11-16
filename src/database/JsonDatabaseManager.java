/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.database;
import backend.models.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class JsonDatabaseManager {
    private static final String USERS_FILE = "users.json";
    private List<User> users;
    public JsonDatabaseManager() {
        users = readUsers(); // load users when starting
    }
    // Read users
    private List<User> readUsers() {
        try (Reader reader = new FileReader(USERS_FILE)) {
            Type userListType = new TypeToken<List<User>>(){}.getType();
            List<User> loadedUsers = new Gson().fromJson(reader, userListType);
            return loadedUsers != null ? loadedUsers : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("users.json not found, starting empty.");
            return new ArrayList<>();
        }
    }
    // Write users
    private void writeUsers() {
        try (Writer writer = new FileWriter(USERS_FILE)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Add a new user
    public boolean addUser(User user) {
        // check duplicate
        for (User u : users) {
            if (u.getUserID() == user.getUserID() || u.getEmail().equalsIgnoreCase(user.getEmail())) {
                return false; // duplicate
            }
        }
        users.add(user);
        writeUsers(); // save
        return true;
    }
    public User findByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }
    //getter
    public List<User> getAllUsers() {
        return users;
    }
}
