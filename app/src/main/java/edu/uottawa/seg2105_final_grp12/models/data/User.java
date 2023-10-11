package edu.uottawa.seg2105_final_grp12.models.data;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class User implements Serializable {
    private FirebaseUser firebaseUser;
    private String username;
    private String firstName;
    private String role;

    // private FirebaseAuth mAuth;

    public User(FirebaseUser fUser, String username, String role) {
        firebaseUser = fUser;
        this.username = username;
        this.role = role;
    }
    public User(String uid, String username, String firstName, String role) {
        this.username = username;
        this.role = role;
    }

    public User() {
    }

    public String getUid() {
        return firebaseUser.getUid();
    }   
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return firebaseUser.getEmail();
    }
    public String getFirstName() {
        return firstName;
    }

    public String getRole() {
        return role;
    }
}
