/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.utils;

/**
 *
 * @author ERR0R
 */

public class AuthService {
    
      public static User login(String email, String password, List<String> errorMessages) {
          errorMessages.clear();
          errorMessages.addAll(InputValidator.validateLoginData(email, password));
        if(!errorMessages.isEmpty()) return null;
          List<User> users = JsonDatabaseManager.loadUsers();
        User foundUser=null;
        for(User u:users) {
            if(u.getEmail().equalsIgnoreCase(email)) {
                foundUser=u;
                break;
            }
        }
        //didn't find  teh email
        if(foundUser == null) {
            errorMessages.add("No account found with this email");
            return null;
        }
        String hashedInput = Hashing.sha256(password);
        if(!hashedInput.equals(foundUser.getPasswordHash())) {
            errorMessages.add("Incorrect password");
            return null;
        }
        return foundUser;
      }
}

