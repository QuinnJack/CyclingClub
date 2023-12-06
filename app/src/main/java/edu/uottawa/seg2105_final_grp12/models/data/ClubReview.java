package edu.uottawa.seg2105_final_grp12.models.data;

public class ClubReview {

    private int rating; // should be 1 to 5
    private String feedback;
    private String reviewerName;

    public ClubReview() {

    }

    public ClubReview(int rating, String feedback, String reviewerName) {
        this.rating = rating;
        this.feedback = feedback;
        this.reviewerName = reviewerName;
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

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }


}
