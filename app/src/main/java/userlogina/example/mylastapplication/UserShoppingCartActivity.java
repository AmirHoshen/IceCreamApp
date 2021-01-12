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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import userlogina.example.mylastapplication.Orders.Order;
import userlogina.example.mylastapplication.Orders.Upload;

public class UserShoppingCartActivity extends AppCompatActivity {

    private RecyclerView userShoppingCartRecyclerView;
    private DatabaseReference dbRef;

    BusinessRecyclerAdapter helperAdapter;

    public static double totalPrice = 0.0;

    private List<Upload> fetchDish;
    public TextView totalPriceText,dishPrice;
    private EditText removeIndex;
    private Button orderButton, removeButton, removeCart;
    private Order benOrder,goldaOrder;
    private ImageView backPressBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shopping_cart);



        buildRecyclerView();


        userShoppingCartRecyclerView = findViewById(R.id.ShoppingCartRecyclerView);
        userShoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dishPrice = findViewById(R.id.IceCreamPrice);
        totalPriceText = findViewById(R.id.totalOrderPrice);
        orderButton = findViewById(R.id.OrderButton);
        removeIndex = findViewById(R.id.editTextNumber);
        removeCart = findViewById(R.id.deleteCart);

        removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.removeValue();
                totalPrice = 0.0;
                totalPriceText.setText("" + totalPrice);
                for (int i = 0; i <= fetchDish.size() - 1; i++) {
                    removeItem(i);
                }

            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Upload up : fetchDish){
                    if(up.getTag().equals("BJN@gmail.com")){
                        benOrder.getDishes().add(up);
                    }else if(up.getTag().equals("golda@gmail.com")){
                        goldaOrder.getDishes().add(up);
                    }
                }
                if(!benOrder.getDishes().isEmpty()) {
                    benOrder.setDate();
                    dbRef = FirebaseDatabase.getInstance().getReference().child("Business").
                            child("nFIRYcoyF7fAE9dXIQbhRKnyEC93").child("orders").getRef();
                    dbRef.push().setValue(benOrder).addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UserShoppingCartActivity.this, "Successfully Added Order from Ben&Jerry's!\nStatus is On prepare", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(!goldaOrder.getDishes().isEmpty()) {
                    goldaOrder.setDate();
                    dbRef = FirebaseDatabase.getInstance().getReference().child("Business").
                            child("OZz7TYO50lQdvGUTkTaXJemSjro2").child("orders").getRef();
                    dbRef.push().setValue(benOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UserShoppingCartActivity.this, "Successfully Added Order from Golda!\nStatus is On prepare", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeIndex.getText().toString().equals(null) || removeIndex.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Legal index value must be inserted!", Toast.LENGTH_LONG).show();
                } else {
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


    public void removeItem(int position) {
        if (position > fetchDish.size() - 1) {
            Toast.makeText(getApplicationContext(), "Chosen position is illegal", Toast.LENGTH_SHORT).show();
        } else {
            if (position == 0 && fetchDish.size() == 0) {
                totalPrice = 0.0;
            } else {
                double itemRemovedPrice = fetchDish.get(position).getPrice();
                Query que = dbRef.orderByChild("falvor").equalTo(fetchDish.get(position).getFalvor());

                que.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getChildren().iterator().next().getRef().removeValue();
                        totalPrice -= itemRemovedPrice;
                        String priceText = "" + (double) totalPrice;
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
        fetchDish = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ShoppingCart").getRef();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Upload dish = ds.getValue(Upload.class);
                    totalPrice += dish.getPrice();
                    fetchDish.add(dish);
                }
                String priceText = totalPrice + "";
                helperAdapter = new BusinessRecyclerAdapter(getApplicationContext(),fetchDish);
                userShoppingCartRecyclerView.setAdapter(helperAdapter);
                totalPriceText.setText(priceText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}