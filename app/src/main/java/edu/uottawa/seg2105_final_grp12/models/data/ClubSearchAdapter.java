package edu.uottawa.seg2105_final_grp12.models.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.uottawa.seg2105_final_grp12.R;

public class ClubSearchAdapter extends ArrayAdapter<User> {

    private Activity context;
    private List<User> clubs;

    public ClubSearchAdapter(Activity context, List<User> clubs) {
        super(context, R.layout.layout_club_search_list, clubs);
        this.context = context;
        this.clubs = clubs;

    }

    @NonNull
    @Override
    public View getView(int position, @
            Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_club_search_list, null, true);

        ImageView imageViewLogo = listViewItem.findViewById(R.id.imageViewLogo);
        TextView tvClubName = listViewItem.findViewById(R.id.tvClubName);
        TextView tvClubSocial = listViewItem.findViewById(R.id.tvClubSocial);
        TextView tvPhone = listViewItem.findViewById(R.id.tvClubPhoneNumber);
        TextView tvContact = listViewItem.findViewById(R.id.tvClubContact);

        User club = clubs.get(position);
        tvClubName.setText(club.getUsername());
        tvClubSocial.setText(club.getRole());
        tvPhone.setText(club.getPhoneNumber());
        tvContact.setText(club.getMainContactName());

        int resourceId;

        if (club.getLogo() != null) {
            resourceId = context.getResources().getIdentifier(club.getLogo(), "drawable", context.getPackageName());
        }
        else {
            resourceId = context.getResources().getIdentifier("default_logo", "drawable", context.getPackageName());
        }

        imageViewLogo.setImageResource(resourceId);

        return listViewItem;
    }

}
