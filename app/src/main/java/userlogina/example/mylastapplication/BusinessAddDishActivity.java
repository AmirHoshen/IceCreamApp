package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusinessAddDishActivity extends AppCompatActivity {

    Button saveBtn;
    EditText dishName, dishDescription, dishPrice;
    FirebaseDatabase database;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_add_dish);

        saveBtn = findViewById(R.id.addDishBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inserting new dish to firebase business owner "orders" branch.
                //NEED--TO-DO
                //NEED--TO-DO
                //NEED--TO-DO
                //NEED--TO-DO
                //NEED--TO-DO
                //NEED--TO-DO
                //NEED--TO-DO
            }
        });
    }
}