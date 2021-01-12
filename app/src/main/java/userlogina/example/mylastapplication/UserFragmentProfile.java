package userlogina.example.mylastapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragmentProfile extends Fragment {

    private TextView name,email,phone;
    private Button signOut;
    private FirebaseUser user;
    private DatabaseReference dbRef;

    public UserFragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        name = (TextView) view.findViewById(R.id.NameTag);
        email = view.findViewById(R.id.EmailTag);
        phone = view.findViewById(R.id.PhoneTag);
        user = FirebaseAuth.getInstance().getCurrentUser();
        signOut = (Button) view.findViewById(R.id.SignOutButton);

        String _user = user.getDisplayName();
        name.setText("Full Name: "+_user);
        email.setText("Email: "+ user.getEmail());

        FirebaseDatabase.getInstance().getReference().child("Users").equalTo(user.getUid()).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        User _phone = snap.getValue(User.class);
                        phone.setText("Phone Number: " + _phone.getPhone());
                    }
                    //_phone.setPhone("11111561"); for later use of change info

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

}