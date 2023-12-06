package edu.uottawa.seg2105_final_grp12.models.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import edu.uottawa.seg2105_final_grp12.R;

public class ReviewAdapter extends ArrayAdapter<ClubReview> {

    private Activity context;
    private List<ClubReview> reviews;
    private DatabaseReference databaseReviews;

    public ReviewAdapter(Activity context, List<ClubReview> reviews) {
        super(context, R.layout.layout_review_list, reviews);
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listView = layoutInflater.inflate(R.layout.layout_review_list, null, false);

        TextView tvAuthor = listView.findViewById(R.id.tv_author);
        TextView tvRatingData = listView.findViewById(R.id.tv_raiting_data);
        TextView tvCommentData = listView.findViewById(R.id.tv_comment_data);

        ClubReview review = reviews.get(position);

        tvAuthor.setText(review.getReviewerName());
        tvRatingData.setText(String.valueOf(review.getRating()));
        tvCommentData.setText(review.getFeedback());

        return listView;
    }

}
