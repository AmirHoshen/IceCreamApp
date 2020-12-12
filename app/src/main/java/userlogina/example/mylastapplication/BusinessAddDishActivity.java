package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import userlogina.example.mylastapplication.Orders.Dish;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BusinessAddDishActivity extends AppCompatActivity {

    private FirebaseAuth m;
    private DatabaseReference dbRef;
    private FirebaseUser business;
    private EditText dishName,dishPrice,dishDescription;
    private Button addDishButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_add_dish);
        business = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Business")
                .child(business.getUid()).child("Dishes").getRef();
        dishName = findViewById(R.id.editTextDishName);
        dishPrice = findViewById(R.id.editTextDishPrice);
        dishDescription = findViewById(R.id.editTextDishDescriptiontMultiLine);
        addDishButton = findViewById(R.id.addDishBtn);
        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDish();
            }
        });

    }

    private void addDish() {
        String _dishName,_dishDescription;
        Double _dishPrice;
        _dishName = dishName.getText().toString();
        _dishDescription = dishDescription.getText().toString();

        if (_dishName.isEmpty()) {
            dishName.setError("Full name is required!");
            dishName.requestFocus();
            return;
        }
        if (_dishDescription.isEmpty()) {
            dishDescription.setError("Full name is required!");
            dishDescription.requestFocus();
            return;
        }
        if (dishPrice.getText().toString().isEmpty()) {
            dishPrice.setError("Full name is required!");
            dishPrice.requestFocus();
            return;
        }
        _dishPrice = Double.parseDouble(dishPrice.getText().toString());


        Dish dish = new Dish(_dishName, _dishDescription, _dishPrice);
        dbRef.push().setValue(dish).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dishPrice.setText(null);
                dishDescription.setText(null);
                dishName.setText(null);
                Toast.makeText(BusinessAddDishActivity.this,"Dish Added!",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BusinessAddDishActivity.this,"Failed To Add Dish!",Toast.LENGTH_LONG).show();
            }
        });

    }
}