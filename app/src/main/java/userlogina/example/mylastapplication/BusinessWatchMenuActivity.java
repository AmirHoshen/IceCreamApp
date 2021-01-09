package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import userlogina.example.mylastapplication.Orders.Dish;

public class BusinessWatchMenuActivity extends AppCompatActivity {


    RecyclerView menuRecyclerView;

    ArrayList<Dish> dishes = new ArrayList<>();
    ArrayList<String> _name = new ArrayList<>();
    ArrayList<String> _description = new ArrayList<>();
    ArrayList<Double> _price = new ArrayList<>();
    ArrayList<Bitmap> _images = new ArrayList<>();
    private ImageView backPressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_watch_menu);

        backPressBtn = findViewById(R.id.busOlderOrderBackPressBtn);
        backPressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessWatchMenuActivity.this, BusinessArea.class));
                finish();
            }
        });

        menuRecyclerView = findViewById(R.id.businessOlderOrderRecycler);
        Collector();
        BusinessRecyclerAdapter recyclerViewAdapter = new BusinessRecyclerAdapter(this,_name, _description, _price);
        menuRecyclerView.setAdapter(recyclerViewAdapter);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void Collector() {
        String curUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query dishList = FirebaseDatabase.getInstance().getReference("Business").child(curUser).child("Dishes").orderByKey();
        dishList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snap: snapshot.getChildren()){
                        Dish dish = snap.getValue(Dish.class);
                        dishes.add(dish);
                        _name.add(dish.getFalvor());
                        _description.add(dish.getDescription());
                        _price.add(dish.getPrice());

                    }
                    BusinessRecyclerAdapter recViewAdapter = new BusinessRecyclerAdapter(getApplicationContext(), _name, _description,_price);

                    menuRecyclerView.setAdapter(recViewAdapter);
                    menuRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }else {
                    Toast.makeText(getApplicationContext(), "No menu exists yet!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
