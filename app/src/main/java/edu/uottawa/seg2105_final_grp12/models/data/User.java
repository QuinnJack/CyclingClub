package edu.uottawa.seg2105_final_grp12.models.data;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private String uid;
    private String username;
    private String email;
    private String firstName;
    private String role;

    private String socialMediaLink;
    private String mainContactName;
    private String phoneNumber;
    private String logo;
    private List<String> eventTypes;
    private List<String> eventNames;
    private List<ClubReview> reviews;
    private int averageRating = 0;


    // private FirebaseAuth mAuth;
    //TODO have reviews come through with userData
    public User(Map<String, String> userData) {
        this.uid = userData.get("uid");
        this.username = userData.get("username");
        this.email = userData.get("email");
        this.role = userData.get("role");
    }
    public User(String uid, String username, String email, String role) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.role = role;
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

    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    public void setSocialMediaLink(String socialMediaLink) {
        this.socialMediaLink = socialMediaLink;
    }

    public String getMainContactName() {
        return mainContactName;
    }

    public void setMainContactName(String mainContactName) {
        this.mainContactName = mainContactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public List<String> getEventNames() {
        return eventNames;
    }

    public void setEventNames(List<String> eventNames) { this.eventNames = eventNames; }

    public String getLogo() { return logo; }

    public void setLogo(String logo) { this.logo = logo; }

    public List<ClubReview> getReviews() { return reviews; }

    public void addReview(ClubReview review) {
        reviews.add(review);
        calculateAverageRating();
    }

    public void clearReviews() {
        if (reviews != null) {
            reviews.clear();
        }
        calculateAverageRating();
    }

    public int getAverageRating() {
        return averageRating;
    }

    private void calculateAverageRating() {

        int ratingSum = 0;

        if (reviews != null) {
            for (ClubReview c: reviews) {
                ratingSum += c.getRating();
            }
        }

        this.averageRating = ratingSum / reviews.size();
    }


}
