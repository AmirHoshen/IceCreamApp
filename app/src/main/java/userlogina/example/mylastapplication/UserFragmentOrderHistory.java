package userlogina.example.mylastapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserFragmentOrderHistory extends Fragment {

    //xml
    private TextView textViewPopUpBName;
    private ListView listView_orderslist;
    //database
    private ArrayList<String> ordersList_string = new ArrayList<>();
    private ArrayAdapter<String> ordersList_adapter;
    private String rest_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference rest_reference = FirebaseDatabase.getInstance().getReference("Users");


    public UserFragmentOrderHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_order_history, container, false);
        listView_orderslist = view.findViewById(R.id.list_oab_activeiorder);
        textViewPopUpBName = view.findViewById(R.id.textView_oab_orderlist);
        rest_reference.child(rest_id).child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot db : dataSnapshot.getChildren()) {
                        double curr_total_price = db.child("price").getValue(Double.class);
                        String total_price_str = String.valueOf(curr_total_price);
                        String order_str = "orderID:" + db.child("orderID").getValue(String.class) + ", " +
                                "date:" + db.child("date").getValue(String.class) + "," + "TotalPrice:" + total_price_str + ".\n";

                        for (DataSnapshot snapshot_dish : db.child("dishes").getChildren()) {
                            String falvor = snapshot_dish.child("falvor").getValue(String.class);
                            int amount = snapshot_dish.child("amount").getValue(Integer.class);
                            order_str += falvor + "(" + amount + ")" + "\n";
                        }
                        order_str += ".";
                        ordersList_string.add(order_str);

                    }
                    ordersList_adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ordersList_string);
                    listView_orderslist.setAdapter(ordersList_adapter);
                } else {
                    Toast.makeText(view.getContext(), "no orders", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return view;
    }
}