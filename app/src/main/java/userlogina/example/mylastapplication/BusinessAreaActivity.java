package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusinessAreaActivity extends AppCompatActivity {


    private FirebaseUser currUser;
    private FirebaseDatabase database;
    private DatabaseReference dBRef;

    private Button pastOrderBtn, watchMenuBtn, addDishBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_area);

        database = FirebaseDatabase.getInstance();
        //dBRef = database.getReference().getRef().........

        pastOrderBtn = findViewById(R.id.doneDealsBtn);
        watchMenuBtn = findViewById(R.id.watchMenuBtn);
        addDishBtn = findViewById(R.id.addMenuBtn);


        pastOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        watchMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessAreaActivity.this,AddDishBussActivity.class));
            }
        });
    }
}