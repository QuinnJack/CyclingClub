package edu.uottawa.seg2105_final_grp12.models;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class User {


    // userID, unique identifier from Firebase
    private String uid;
    private String username;
    private String email;
    private String role;

    // private FirebaseAuth mAuth;

    public User() {
    }
    public User(String uid, String username, String email, String role) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.role = role;
        // this.mAuth = FirebaseAuth.getInstance();
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

    public String getRole() {
        return role;
    }

    // TODO
    public boolean login(String username, String password) {
        return true;
    }
    public void logout() {
        //mAuth.signOut();
    }

    public boolean createAccount(String username, String password) {
        return true;
    }
}
