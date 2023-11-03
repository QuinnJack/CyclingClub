package edu.uottawa.seg2105_final_grp12.models.data;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {
    private String uid;
    private String username;
    private String email;
    private String firstName;
    private String role;

    // private FirebaseAuth mAuth;

    public User(Map<String, String> userData) {
        this.uid = userData.get("uid");
        this.username = userData.get("username");
        this.email = userData.get("email");
        this.role = userData.get("role");
    }
    public User(String uid, String username, String firstName, String role) {
        this.uid = uid;
        this.username = username;
        this.firstName = firstName;
        this.role = role;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }   
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getRole() {
        return role;
    }
}
