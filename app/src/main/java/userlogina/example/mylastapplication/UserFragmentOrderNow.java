package userlogina.example.mylastapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class UserFragmentOrderNow extends Fragment {

    private ImageButton bJBtn, goldaBtn;

    public UserFragmentOrderNow() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_order_now, container, false);

        goldaBtn = (ImageButton) view.findViewById(R.id.goldaBtn);
        bJBtn = (ImageButton) view.findViewById(R.id.benjerryBtn);

        goldaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), goldaActivityMenu.class));
            }
        });

        bJBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BenJerryActivityMenu.class));
            }
        });


       return view;
    }
}