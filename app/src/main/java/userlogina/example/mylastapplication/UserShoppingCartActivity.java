package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    public static double totalPrice = 0.0;

    List<Dish> fetchDish;
    public TextView totalPriceText;
    private EditText removeIndex;
    private Button orderButton, removeButton, removeCart;

    private ImageView backPressBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shopping_cart);


        createExampleList();
        buildRecyclerView();



        userShoppingCartRecyclerView = findViewById(R.id.ShoppingCartRecyclerView);
        userShoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalPriceText = findViewById(R.id.totalOrderPrice);
        orderButton = findViewById(R.id.OrderButton);
        removeIndex = findViewById(R.id.editTextNumber);
        removeCart = findViewById(R.id.deleteCart);

        removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.removeValue();
                totalPrice = 0.0;
                totalPriceText.setText( ""+totalPrice);
                for(int i=0; i<=fetchDish.size()-1; i++){
                    removeItem(i);
                }

            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeIndex.getText().toString().equals(null) || removeIndex.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Legal index value must be inserted!", Toast.LENGTH_LONG).show();
                }else{
                    int position = (Integer.parseInt(removeIndex.getText().toString()));
                    removeItem(position);
                }
            }
        });


        backPressBtn = findViewById(R.id.busOlderOrderBackPressBtn);
        backPressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        fetchDish = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Shopping Cart").getRef();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Dish dish = ds.getValue(Dish.class);
                    totalPrice += dish.getPrice();
                    fetchDish.add(dish);
                }
                String priceText = totalPrice +"";
                helperAdapter = new HelperAdapter(fetchDish);
                userShoppingCartRecyclerView.setAdapter(helperAdapter);
                totalPriceText.setText(priceText);
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
    public void removeItem(int position){
        if(position>fetchDish.size()-1){
            Toast.makeText(getApplicationContext(),"Chosen position is illegal", Toast.LENGTH_SHORT).show();
        }else{
            if(position == 0 && fetchDish.size() == 0){
                totalPrice = 0.0;
            }else{
                double itemRemovedPrice = fetchDish.get(position).getPrice();
                Query que = dbRef.orderByChild("falvor").equalTo(fetchDish.get(position).getFalvor());

                que.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getChildren().iterator().next().getRef().removeValue();
                        totalPrice -=  itemRemovedPrice;
                        String priceText = ""+ (double)totalPrice;
                        totalPriceText.setText(priceText);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                fetchDish.remove(position);
                helperAdapter.notifyItemRemoved(position);
            }

        }
    }

    public void buildRecyclerView() {
    }
    public void createExampleList(){

    }
}