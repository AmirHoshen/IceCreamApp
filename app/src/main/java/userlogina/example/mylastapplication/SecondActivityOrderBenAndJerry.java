package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

//import userlogina.example.mylastapplication.Orders.Dish;
import userlogina.example.mylastapplication.Orders.Upload;

public class SecondActivityOrderBenAndJerry extends AppCompatActivity {

    private ImageView mainImageView, menuItemBackBtnBJ;
    private TextView title, description,price;

    private Button addToCartBtn;

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private Context context;

    //EditText dishPrice;
    double _price;
    String dishTitle, dishDescription;
    String image;
    private String tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_order_ben_and_jerry);


        mainImageView = findViewById(R.id.mainImageView);
        title = findViewById(R.id.orderTitle);
        description = findViewById(R.id.orderDescription);
        price = findViewById(R.id.dishPriceView);

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

        if(tag == null){
            Toast.makeText(getApplicationContext(), "Please contact support!", Toast.LENGTH_SHORT).show();
        }else if(tag.equals("BJN@gmail.com")){
            dbRef = database.getReference().child("Users").child(user.getUid()).child("Shopping Cart Ben&Jerry's").getRef();
        }else if(tag.equals("golda@gmail.com")){
            dbRef = database.getReference().child("Users").child(user.getUid()).child("Shopping Cart Golda").getRef();
        }

        dbRef.push().setValue(new Upload(title.getText().toString(),description.getText().toString(),Double.parseDouble(price.getText().toString()),image,image)).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        if(getIntent().hasExtra("tag") && getIntent().hasExtra("image") && getIntent().hasExtra("name") && getIntent().hasExtra("description") && getIntent().hasExtra("price") ){

            dishTitle = getIntent().getStringExtra("name");
            dishDescription = getIntent().getStringExtra("description");
            _price = getIntent().getDoubleExtra("price",1);
            image =  getIntent().getStringExtra("image");
            tag = getIntent().getStringExtra("tag");

        }else{
            Toast.makeText(this, "No Data, please contact support",Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        title.setText(dishTitle);
        description.setText(dishDescription);
        mainImageView.setTag(tag);
        Picasso.with(context)
                .load(image)
                .fit()
                .centerCrop()
                .into(mainImageView);
        price.setText(""+_price);
    }
}