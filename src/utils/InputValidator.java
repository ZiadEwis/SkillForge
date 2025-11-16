/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package backend.utils;
/**
 *
 * @author ERR0R
 */
public class InputValidator {

    
     public static List<String> validateSignupData(String name,String email,String password,String userId,String role){
         List<String> errorMessages = new ArrayList<>();
         //empty check
         if(isEmpty(name))
             errorMessages.add("Cannot add empty name");
         if(isEmpty(password))
             errorMessages.add("Cannot add empty password");
         if(isEmpty(userId))
             errorMessages.add("Cannot add empty userId");
         if(isEmpty(email))
             errorMessages.add("Cannot add empty email");
         if(isEmpty(role))
             errorMessages.add("Cannot add empty role");
         
         //validationss
         if(!isValidName(name))
             errorMessages.add("Name can only contain letters and spaces");
         if(!isValidPassword(password))
             errorMessages.add("Password must contain uppercase,lowercase,and digits");
         if(!EmailValidator.isValidEmail(email))
             errorMessages.add("Invalid email format");
         if(!isValidRole(role))
             errorMessages.add("Role must be Student, Instructor, or User");
         if (JsonDatabaseManager.userExists(userId))
             errorMessages.add("userID already exists");
        return errorMessages;
    }
    public static List<String> validateLoginData(String email, String password){
        List<String> errorMessages = new ArrayList<>();
        //empty check
         if(isEmpty(email))
             errorMessages.add("Email cannot be empty");
         if(isEmpty(password))
             errorMessages.add("Password cannot be empty");
         
         //validations
         if(!EmailValidator.isValidEmail(email))
             errorMessages.add("Invalid email format");
         if(!isValidPassword(password))
             errorMessages.add("Password must contain uppercase,lowercase,and digits");
         if(!errorMessages.isEmpty())
        return null; 
        return errorMessages;
        
    }
    public static boolean isEmpty(String input){
        if(input == null||input.isEmpty())
            return true;
        else
            return false;
    }
    public static boolean isValidName(String name){
        if(name == null||name.isEmpty())
            return false;
        for (char c : name.toCharArray()) {
        if (!Character.isLetter(c)&& c!= ' ')
            return false;
    }
    return true;
}
    public static boolean isValidPassword(String password){
        int capitalLetters=0,smallLetters=0,numbers=0;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)&& Character.isUpperCase(c))
                capitalLetters++;
            else if (Character.isLetter(c)&& Character.isLowerCase(c))
                smallLetters++;
            else if (Character.isDigit(c))
                numbers++;
            else
                return false;
            }
        if(capitalLetters>0&&smallLetters>0&&numbers>0)
            return true;
        else
            return false;
        }
    public static boolean isValidRole(String role){
        if(role.equalsIgnoreCase("User")||role.equalsIgnoreCase("Instructor")||role.equalsIgnoreCase("Student"))
            return true;
        else
            return false;
    }
}
