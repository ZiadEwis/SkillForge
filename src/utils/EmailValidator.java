/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package backend.utils;

/**
 *
 * @author ERR0R
 */
public class EmailValidator {
    public static boolean isValidEmail(String email){
        if(email.isEmpty()) return false;
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex != email.lastIndexOf('@')) return false;
        if (atIndex >= email.length() - 1) return false;//chars after @
        return true;
    }
}
