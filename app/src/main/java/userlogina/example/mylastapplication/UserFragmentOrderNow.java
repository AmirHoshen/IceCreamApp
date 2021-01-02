package userlogina.example.mylastapplication;

import android.content.Intent;
import android.os.Bundle;

import static userlogina.example.mylastapplication.R.array.Cities;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserFragmentOrderNow extends Fragment implements AdapterView.OnItemSelectedListener {

    private ImageButton bJBtn, goldaBtn;
    private Button moveToShoppingCartViewActivity;
    private String city_from_spinner = "Haifa";
    private String type_from_cb = "Golda";
    boolean img_visible = true;//(If image's visibility is set to true in xml file else false)

    public UserFragmentOrderNow() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_order_now, container, false);


        goldaBtn = (ImageButton) view.findViewById(R.id.goldaBtn);
        bJBtn = (ImageButton) view.findViewById(R.id.benjerryBtn);
        moveToShoppingCartViewActivity = (Button) view.findViewById(R.id.cartViewBtn);

        bJBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BenJerryActivityMenu.class));
            }
        });


        goldaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GoldaActivityMenu.class));
            }
        });

//        moveToShoppingCartViewActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //fetchUserOrderDataFromFireBase();
//                startActivity(new Intent(getActivity(), UserShoppingCartActivity.class));
//            }
//        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city_from_spinner = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), city_from_spinner, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}