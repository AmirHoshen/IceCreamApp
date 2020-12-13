package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import userlogina.example.mylastapplication.Orders.Dish;

public class UserShoppingCartActivity extends AppCompatActivity {

    private RecyclerView userShoppingCartRecyclerView;


    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference dbRef;

    HelperAdapter helperAdapter;

    private String dishTitle[];
    private String dishDescription[];
    private double dishPrices[];

    List<Dish> fetchDish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shopping_cart);

        userShoppingCartRecyclerView = findViewById(R.id.ShoppingCartRecyclerView);
        userShoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDish = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Shopping Cart").getRef();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Dish dish = ds.getValue(Dish.class);
                    fetchDish.add(dish);
                }
                helperAdapter = new HelperAdapter(fetchDish);
                userShoppingCartRecyclerView.setAdapter(helperAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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