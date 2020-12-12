package userlogina.example.mylastapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserFragmentProfile extends Fragment {

    private TextView name;
    private Button signOut;

    public UserFragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
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
        name.setText("Welcome "+_user);

        // Inflate the layout for this fragment
        return view;
    }

}