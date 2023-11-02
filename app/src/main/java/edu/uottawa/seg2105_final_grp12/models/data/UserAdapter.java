package edu.uottawa.seg2105_final_grp12.models.data;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.uottawa.seg2105_final_grp12.R;

public class UserAdapter  extends ArrayAdapter<User> {
    private Activity context;
    private List<User> users;

    public UserAdapter(Activity context, List<User> users) {
        super(context, R.layout.layout_event_list, users);
        this.context = context;
        this.users = users;

    }

    @NonNull
    @Override
    public View getView(int position, @
            Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);

        TextView tvUserName = listViewItem.findViewById(R.id.tvUserName);
        TextView tvUserRole = listViewItem.findViewById(R.id.tvUserRole);
        Button btnDeleteUser = listViewItem.findViewById(R.id.btnDeleteUser);

        User user = users.get(position);
        tvUserName.setText(user.getUsername());
        tvUserRole.setText(user.getRole());

        btnDeleteUser.setOnClickListener(view -> {
            DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
            databaseUsers.removeValue(); // Deletes the user from Firebase
        });

        return listViewItem;
    }
}
