package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import userlogina.example.mylastapplication.Orders.Upload;

//import userlogina.example.mylastapplication.Orders.Dish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GoldaActivityMenu extends AppCompatActivity {

    Button previousPageGoldaBtn, shoppingCartGolda;
    RecyclerView goldaRecyclerView;

    ArrayList<Upload> dishes = new ArrayList<>();
    ArrayList<String> _name = new ArrayList<>();
    ArrayList<String> _description = new ArrayList<>();
    ArrayList<Double> _price = new ArrayList<>();
    ArrayList<ImageView> _images = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golda_menu);

        goldaRecyclerView = findViewById(R.id.recyclerViewGolda);
        previousPageGoldaBtn = findViewById(R.id.previousPageGoldaBtn);
        shoppingCartGolda = findViewById(R.id.shoppingCartGolda);

        previousPageGoldaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shoppingCartGolda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoldaActivityMenu.this, UserShoppingCartActivity.class));
                finish();
            }
        });

        //query to put info in s1,...,s3
        //Query dishList = FirebaseDatabase.getInstance().getReference("Business").child("OZz7TYO50lQdvGUTkTaXJemSjro2").child("Dishes").getRef();
        Collector();


        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, _name, _description, _price, null);

        goldaRecyclerView.setAdapter(recViewAdapter);
        goldaRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void Collector() {
        Query dishList = FirebaseDatabase.getInstance().getReference("Business").child("OZz7TYO50lQdvGUTkTaXJemSjro2").child("Dishes").getRef();
        dishList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        System.out.println("Dish located!");
                        Upload d = dataSnapshot.getValue(Upload.class);
                        dishes.add(d);
                    }
                else
                    System.out.println("Not getting Query Right!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Business").child("OZz7TYO50lQdvGUTkTaXJemSjro2").child("Dishes").child("imageUrl");
        for (Upload d : dishes) {
            _name.add(d.getFalvor().toString());
            _description.add(d.getDescription());
            _price.add(d.getPrice());
            ImageView imgView = findViewById(R.id.myImageView);
            _images.add(imgView);
            Glide.with(this).load(storageReference).into(imgView);
        }

        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, _name, _description, _price, _images);
        goldaRecyclerView.setAdapter(recViewAdapter);
        goldaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

