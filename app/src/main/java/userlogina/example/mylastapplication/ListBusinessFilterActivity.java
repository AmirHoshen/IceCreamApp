package userlogina.example.mylastapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListBusinessFilterActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference business;
    private ArrayList<Business> business_current = new ArrayList<>();
    private ArrayList<String> business_list = new ArrayList<>();
    private ArrayAdapter<String> busniess_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_business_filter
        );

        listView = (ListView) findViewById(R.id.list_select_menu);
        business = FirebaseDatabase.getInstance().getReference("Business"); //get reference to Restaurant

        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("location");
        String type = bundle.getString("type");


        Query query = business.orderByChild("location").equalTo(city);// order the database by location
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!business_list.isEmpty()) {
                    business_list.clear();
                    business_current.clear();
                    busniess_adapter.clear();
                    listView.clearAnimation();
                }

                if (dataSnapshot.exists()) { //if there is a restaurants in this area
                    String rest_string, rest_type;
                    for (DataSnapshot db : dataSnapshot.getChildren()) {
                        rest_type = db.child("type").getValue(String.class);
                        if (rest_type.equals(type)) {
                            rest_string = db.child("businessName").getValue(String.class);
                            Business temp_rest = db.getValue(Business.class);
                            business_current.add(temp_rest);
                            business_list.add(rest_string);
                        }
                        busniess_adapter = new ArrayAdapter<String>(ListBusinessFilterActivity.this, R.layout.listext, business_list);
                        listView.setAdapter(busniess_adapter);
                    }

                } else {
                    business_list.add("No restaurants");
                    busniess_adapter = new ArrayAdapter<String>(ListBusinessFilterActivity.this, R.layout.listext, business_list);
                    listView.setAdapter(busniess_adapter);
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent restchoose = new Intent(ListBusinessFilterActivity.this, BenJerryActivityMenu.class);
                        //
                        restchoose.putExtra("rest_id", business_current.get(i).getUID());
                        startActivity(restchoose);
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
