package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusinessArea extends AppCompatActivity {

    private Button olderOrders, watchMenu, addDish, logout;
    private TextView backToMainBanner;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_area);


        olderOrders = findViewById(R.id.businessOlderOrders);
        watchMenu = findViewById(R.id.businessWatchMenu);
        addDish = findViewById(R.id.businessAddDish);
        logout = findViewById(R.id.BusinessSignOutButton);

        olderOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessArea.this, BusinessOlderOrdersActivity.class));

            }
        });

        watchMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessArea.this, BusinessWatchMenuActivity.class));
                finish();
            }
        });

        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessArea.this, BusinessAddDishActivity.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(BusinessArea.this, MainActivity.class));
                finish();
            }
        });


    }
}