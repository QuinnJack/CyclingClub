package edu.uottawa.seg2105_final_grp12.models.data;

public class Admin extends User {

    private static final String predefinedUsername = "admin";
    private static final String predefinedPassword = "admin";

    public String getPredefinedUsername() {
        return predefinedUsername;
    }
    public String getPredefinedPassword() {
        return predefinedUsername;
    }


    public Admin() {}

    public Admin(String uid, String email) {
        super(uid, predefinedUsername, email, "Admin");
    }

}
