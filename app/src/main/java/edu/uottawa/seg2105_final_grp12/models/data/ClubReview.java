package edu.uottawa.seg2105_final_grp12.models.data;

public class ClubReview {

    private int rating; // should be 1 to 5
    private String feedback;

    public ClubReview(int rating, String feedback) {
        this.rating = rating;
        this.feedback = feedback;
    }

    public int getRating() {
        return rating;
    }

    // ensure that rating is between 1 and 5, inclusively
    public boolean setRating(int rating) {
        if (rating < 1 || rating > 5) {
            return false;
        }
        this.rating = rating;
        return true;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback() {
        this.feedback = feedback;
    }


}
