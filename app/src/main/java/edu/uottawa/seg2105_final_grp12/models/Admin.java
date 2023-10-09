package edu.uottawa.seg2105_final_grp12.models;

public class Admin extends User {

    private static final String predefinedUsername = "admin";
    private static final String predefinedPassword = "admin";

    public Admin() {}

    public Admin(String uid) {
        super(uid, predefinedUsername, "Administrator", "admin");
    }




}
