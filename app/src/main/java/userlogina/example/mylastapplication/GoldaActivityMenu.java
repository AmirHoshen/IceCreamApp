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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GoldaActivityMenu extends AppCompatActivity {

    Button previousPageGoldaBtn, shoppingCartGolda;
    RecyclerView goldaRecyclerView;
    private DatabaseReference mRef;
    private List<Upload> mUpload = new ArrayList<>();
    BusinessRecyclerAdapter recViewAdapter;


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
        goldaRecyclerView = findViewById(R.id.recyclerViewGolda);
        Collector();
        BusinessRecyclerAdapter recyclerViewAdapter = new BusinessRecyclerAdapter(this, mUpload);
        goldaRecyclerView.setAdapter(recyclerViewAdapter);
        goldaRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void Collector() {
        mRef = FirebaseDatabase.getInstance().getReference().child("Business").
                child("OZz7TYO50lQdvGUTkTaXJemSjro2").child("Dishes").getRef();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Upload up = snap.getValue(Upload.class);
                    mUpload.add(up);
                }
                recViewAdapter = new BusinessRecyclerAdapter(GoldaActivityMenu.this, mUpload);
                goldaRecyclerView.setAdapter(recViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GoldaActivityMenu.this, "Database ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        BusinessRecyclerAdapter recViewAdapter = new BusinessRecyclerAdapter(this, mUpload);
        goldaRecyclerView.setAdapter(recViewAdapter);
        goldaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusinessRecyclerAdapter recViewAdapter = new BusinessRecyclerAdapter(this, mUpload);
        goldaRecyclerView.setAdapter(recViewAdapter);
        goldaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

