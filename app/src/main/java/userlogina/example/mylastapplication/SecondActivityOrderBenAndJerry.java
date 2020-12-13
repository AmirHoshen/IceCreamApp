package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import userlogina.example.mylastapplication.Orders.Dish;

public class SecondActivityOrderBenAndJerry extends AppCompatActivity {

    private ImageView mainImageView, menuItemBackBtnBJ;
    private TextView title, description;

    private Button addToCartBtn;

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    EditText dishPrice;
    double price;
    String dishTitle, dishDescription;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_order_ben_and_jerry);


        mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.orderTitle);
        description = findViewById(R.id.orderDescription);
//        dishPrice = findViewById(R.id.priceTageDish);

        getData();
        setData();

        addToCartBtn = findViewById(R.id.btnAddToCartBJ);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        menuItemBackBtnBJ = findViewById(R.id.menuItemBackBtn);
        menuItemBackBtnBJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addToCart() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference().child("Users").child(user.getUid()).child("Shopping Cart").getRef();


        dbRef.push().setValue(new Dish(title.getText().toString(),description.getText().toString(),29.9,1)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SecondActivityOrderBenAndJerry.this, "Dish added successfully to shopping cart", Toast.LENGTH_LONG).show();
                    finish();

                }else{
                    Toast.makeText(SecondActivityOrderBenAndJerry.this, "Dish order has been canceled, please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void getData(){
        if(getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2") ){

            dishTitle = getIntent().getStringExtra("data1");
            dishDescription = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage",1);
//            price = Double.parseDouble(dishPrice.getText().toString());

        }else{
            Toast.makeText(this, "No Data, please contact support",Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        title.setText(dishTitle);
        description.setText(dishDescription);
        mainImageView.setImageResource(myImage);

    }
}