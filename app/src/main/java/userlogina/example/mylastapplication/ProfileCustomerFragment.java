package userlogina.example.mylastapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileCustomerFragment extends Fragment {

    private TextView name;
    private Button signOut;

    public ProfileCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_customer, container, false);
        name = (TextView) view.findViewById(R.id.NameTag);
        signOut = (Button) view.findViewById(R.id.SignOutButton);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });
        String _user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        System.out.println(_user);
        name.setText("Welcome "+_user);

        // Inflate the layout for this fragment
        return view;
    }

}