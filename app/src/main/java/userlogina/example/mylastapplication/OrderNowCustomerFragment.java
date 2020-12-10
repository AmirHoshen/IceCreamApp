package userlogina.example.mylastapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class OrderNowCustomerFragment extends Fragment {

    private ImageButton bJBtn, goldaBtn;

    public OrderNowCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_now_customer, container, false);

        goldaBtn = (ImageButton) view.findViewById(R.id.goldaBtn);
        bJBtn = (ImageButton) view.findViewById(R.id.benjerryBtn);

        goldaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GoldaActivityMenu.class));
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