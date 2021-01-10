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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import userlogina.example.mylastapplication.Orders.Dish;
import userlogina.example.mylastapplication.Orders.Upload;

public class BusinessWatchMenuActivity extends AppCompatActivity {


    private RecyclerView menuRecyclerView;
    private BusinessRecyclerAdapter mAdapter;

    private DatabaseReference mRef;
    private List<Upload> mUpload;
    private List<String> taste;
    private List<String> description;
    private List<Double> price;

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
        BusinessRecyclerAdapter recyclerViewAdapter = new BusinessRecyclerAdapter(this, mUpload);
        menuRecyclerView.setAdapter(recyclerViewAdapter);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void Collector() {

        String curUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUpload = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("Business").child(curUser).child("Dishes");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn: snapshot.getChildren()){

                    Upload upload = sn.getValue(Upload.class);
                    mUpload.add(upload);
                }

                mAdapter = new BusinessRecyclerAdapter(BusinessWatchMenuActivity.this, mUpload);
                menuRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BusinessWatchMenuActivity.this, "Database ERROR", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
