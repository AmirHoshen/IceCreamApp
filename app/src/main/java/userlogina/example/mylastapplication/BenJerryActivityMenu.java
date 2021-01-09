package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import userlogina.example.mylastapplication.Orders.Dish;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BenJerryActivityMenu extends AppCompatActivity {

    Button previousPageBenJerryBtn, shoppingCartBJ;
    RecyclerView benJerry;
    RecyclerViewAdapter recViewAdapter;
    ArrayList<Dish> dishList = new ArrayList<>();
    ArrayList<String> _name = new ArrayList<>();
    ArrayList<String> _description = new ArrayList<>();
    ArrayList<Bitmap> _images = new ArrayList<>();
    ArrayList<Double> _price = new ArrayList<>();
    ArrayList<Uri> images = new ArrayList<>();


    Query Dishes = FirebaseDatabase.getInstance().getReference().child("Business").child("nFIRYcoyF7fAE9dXIQbhRKnyEC93").child("Dishes").getRef();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ben_jerry_menu);



        previousPageBenJerryBtn = (Button)findViewById(R.id.previousPageBenJerryBtn);
        shoppingCartBJ = (Button)findViewById(R.id.shoppingCartBenJerryBtn);

        previousPageBenJerryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shoppingCartBJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BenJerryActivityMenu.this, UserShoppingCartActivity.class));
                finish();
            }
        });
        synchronized (this){
            Collector();
        }

        benJerry = findViewById(R.id.benjerryRecyclerView);

    }

    public synchronized void Collector(){
        Dishes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public synchronized void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println(snapshot.getKey());
                if(snapshot.exists())
                    for(DataSnapshot snap: snapshot.getChildren()){
                        System.out.println("Dish located!");
                        Dish dish = snap.getValue(Dish.class);
                        dishList.add(dish);
                        FirebaseStorage.getInstance().getReference().child(dish.getImageUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public synchronized void onSuccess(Uri uri) {
                                System.out.println("Image Got");
                                images.add(uri);
                                Toast.makeText(BenJerryActivityMenu.this,"Image Getting!",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public synchronized void onFailure(@NonNull Exception exception) {
                                Toast.makeText(BenJerryActivityMenu.this,"Image Getting Error!",Toast.LENGTH_LONG).show();
                            }
                        });
                        System.out.println(dish.getImageUrl());
                        recViewAdapter = new RecyclerViewAdapter(getApplication(), dishList,images);
                        benJerry.setAdapter(recViewAdapter);
                        benJerry.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        System.out.println("Got her0e");
                    }
                else
                    Toast.makeText(BenJerryActivityMenu.this,"Dishe Getting!",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, _name, _description, _price, null);
        benJerry.setAdapter(recViewAdapter);
        benJerry.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, _name, _description, _price, null);
        benJerry.setAdapter(recViewAdapter);
        benJerry.setLayoutManager(new LinearLayoutManager(this));
    }



}