package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import userlogina.example.mylastapplication.Orders.Dish;
import userlogina.example.mylastapplication.Orders.Upload;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BenJerryActivityMenu extends AppCompatActivity {

    Button previousPageBenJerryBtn, shoppingCartBJ;
    RecyclerView benJerry;
    BusinessRecyclerAdapter recViewAdapter;
    private List<Upload> mUpload = new ArrayList<>();
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ben_jerry_menu);


        previousPageBenJerryBtn = (Button) findViewById(R.id.previousPageBenJerryBtn);
        shoppingCartBJ = (Button) findViewById(R.id.shoppingCartBenJerryBtn);

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


        benJerry = findViewById(R.id.benjerryRecyclerView);
        Collector();
        BusinessRecyclerAdapter recyclerViewAdapter = new BusinessRecyclerAdapter(this, mUpload);
        benJerry.setAdapter(recyclerViewAdapter);
        benJerry.setLayoutManager(new LinearLayoutManager(this));

    }

    public void Collector() {
        mRef = FirebaseDatabase.getInstance().getReference().child("Business").
                child("nFIRYcoyF7fAE9dXIQbhRKnyEC93").child("Dishes").getRef();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    System.out.println("Dish located!");
                    Upload up = snap.getValue(Upload.class);
                    mUpload.add(up);
                }
                recViewAdapter = new BusinessRecyclerAdapter(BenJerryActivityMenu.this, mUpload);
                benJerry.setAdapter(recViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BenJerryActivityMenu.this, "Database ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        BusinessRecyclerAdapter recViewAdapter = new BusinessRecyclerAdapter(this, mUpload);
        benJerry.setAdapter(recViewAdapter);
        benJerry.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusinessRecyclerAdapter recViewAdapter = new BusinessRecyclerAdapter(this, mUpload);
        benJerry.setAdapter(recViewAdapter);
        benJerry.setLayoutManager(new LinearLayoutManager(this));
    }


}