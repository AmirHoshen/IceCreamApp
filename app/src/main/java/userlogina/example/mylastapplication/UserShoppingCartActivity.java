package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserShoppingCartActivity extends AppCompatActivity {

    private RecyclerView userShoppingCartRecyclerView;
    private RecyclerViewAdapter RecyclerAdapter;

    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference dbRef;

    private String dishTitle[];
    private String dishDescription[];
    private double dishPrices[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shopping_cart);

        userShoppingCartRecyclerView = findViewById(R.id.ShoppingCartRecyclerView);

        //reference to user database shopping cart branch id

        //collecting information from all dishes to sum up the order and display it 4 user.

        //(String)dishTitle = collect dish titles from user shopping cart into this string array
        //(String)dishTitle = collect dish description from user shopping cart into this string array
        //(Double)dishTitle = collect dish prices from user shopping cart into this string array

        //1.save sums of dish prices array in int parameter and display it to user
        //2.set on click listener button to buy now this order
        //2.open new branch in user called olderOrders and push this data in
        //3.send this order details into business new branch called Orders
        //4.if data sent successfully push this data into this user new branch called OrderHistory
        // and empty this user shopping cart.


    }
}