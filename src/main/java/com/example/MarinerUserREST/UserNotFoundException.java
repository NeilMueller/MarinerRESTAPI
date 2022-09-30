/**
 * Exception called when user cannot be found
 */

package com.example.MarinerUserREST;

public class UserNotFoundException extends RuntimeException{
    UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
