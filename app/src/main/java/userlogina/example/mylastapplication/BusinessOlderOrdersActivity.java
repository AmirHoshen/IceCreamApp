package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class BusinessOlderOrdersActivity extends AppCompatActivity {

    private ImageView backPress;
    //xml
    private TextView ordertext;
    private ListView listView_orderslist;
    private Button button_done;
    //database
    private ArrayList<String> ordersList_string = new ArrayList<>();
    private ArrayAdapter<String> ordersList_adapter;
    private String rest_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference rest_reference = FirebaseDatabase.getInstance().getReference("Business");
    private DatabaseReference order_ref = FirebaseDatabase.getInstance().getReference("Business");
    private DatabaseReference user_order_ref = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_older_orders);

        backPress = findViewById(R.id.busOlderOrderBackPressBtn);
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessOlderOrdersActivity.this, BusinessArea.class));
                finish();
            }
        });

        listView_orderslist = findViewById(R.id.list_oab_activeiorder);
        ordertext = findViewById(R.id.textView_oab_orderlist);
        rest_reference.child(rest_id).child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot db : dataSnapshot.getChildren()) {
                        double curr_total_price = db.child("price").getValue(Double.class);
                        String total_price_str = String.valueOf(curr_total_price);
                        String order_str = "orderID:" + db.child("orderID").getValue(String.class) + ", " +
                                "customerName:" + db.child("customerName").getValue(String.class) + ", " +
                                "date:" + db.child("date").getValue(String.class) + "," + "TotalPrice:" + total_price_str + ".\n";

                        for (DataSnapshot snapshot_dish : db.child("dishes").getChildren()) {
                            String falvor = snapshot_dish.child("falvor").getValue(String.class);
                            int amount = snapshot_dish.child("amount").getValue(Integer.class);
                            order_str += falvor + "(" + amount + ")" + "\n";
                        }
                        order_str += ".";
                        ordersList_string.add(order_str);

                    }
                    ordersList_adapter = new ArrayAdapter<String>(BusinessOlderOrdersActivity.this, android.R.layout.simple_list_item_1, ordersList_string);
                    listView_orderslist.setAdapter(ordersList_adapter);
                } else {
                    Toast.makeText(BusinessOlderOrdersActivity.this, "no orders", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}