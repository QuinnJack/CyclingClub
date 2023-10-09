package edu.uottawa.seg2105_final_grp12.models;

public class Participant extends User {



    public Participant() {}

    public Participant(String uid, String username, String email) {
        super(uid, username, email, "participant");
    }



}