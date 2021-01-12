package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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
    private Order benOrder,goldaOrder,userOrder;
    private ImageView backPressBtn;
    private DatabaseReference dbRefBen;
    private DatabaseReference dbRefGolda;
    private static boolean BFlag = false;
    private static boolean GFlag = false;
    private DatabaseReference dbRefUser;


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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                benOrder = new Order();
                goldaOrder = new Order();
                userOrder = new Order();
                userOrder.setDate();
                for(Upload up : fetchDish){
                        userOrder.getDishes().add(up);
                    //if(up.getTag().equals("bjn@gmail.com")){
                    if(up.getTag().equals("mot@gmail.com")){
                        benOrder.getDishes().add(up);
                    }else if(up.getTag().equals("golda@gmail.com")){
                        goldaOrder.getDishes().add(up);
                    }
                }
                if(!benOrder.getDishes().isEmpty()) {
                    benOrder.setDate();
                    benOrder.setStatus("In progress");
                    dbRefBen = FirebaseDatabase.getInstance().getReference().child("Business").child("8BNDK7H7taPpWpbQPmywdGn395G2").child("Orders").getRef();
                            //child("hezz5qgKXDgnRiL6vEG2ZU0vJ6x1").child("Orders").getRef();
                    dbRefBen.push().setValue(benOrder).addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                        public void onSuccess(Void aVoid) {
                            BFlag = true;
                            Toast.makeText(UserShoppingCartActivity.this, "Successfully Added Order from Ben&Jerry's!\nStatus is On prepare", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            BFlag = false;
                        }
                    });
                }
                if(!goldaOrder.getDishes().isEmpty()) {
                    goldaOrder.setDate();
                    dbRefGolda = FirebaseDatabase.getInstance().getReference().child("Business").
                            child("OZz7TYO50lQdvGUTkTaXJemSjro2").child("Orders").getRef();
                    dbRefGolda.push().setValue(goldaOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            GFlag = true;
                            Toast.makeText(UserShoppingCartActivity.this, "Successfully Added Order from Golda!\nStatus is On prepare", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            GFlag = false;
                        }
                    });
                }
                dbRefUser = FirebaseDatabase.getInstance().getReference().child("Users").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").getRef();
                dbRefUser.push().setValue(userOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        notificationOnComplete();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
                totalPrice = 0.0;
                totalPriceText.setText("" + totalPrice);
                for (int i = 0; i <= fetchDish.size() - 1; i++) {
                    removeItem(i);
                }
                dbRef.removeValue();
                startActivity(new Intent(UserShoppingCartActivity.this,UserLandPageMainActivity.class));
                finish();
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
                        if(snapshot.exists()) {
                            snapshot.getChildren().iterator().next().getRef().removeValue();
                            totalPrice -= itemRemovedPrice;
                            String priceText = "" + (double) totalPrice;
                            totalPriceText.setText(priceText);
                        }
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificationOnComplete(){
        if(BFlag == true || GFlag == true ){
            NotificationChannel mChannel = new NotificationChannel("GlidaLee_1", "GlidaLee", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(UserShoppingCartActivity.this,"GlidaLee_1")
                    .setContentTitle("GlidaLee")
                    .setContentText("Order Has Been Set")
                    .setSmallIcon(R.drawable.b_jlogo)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setChannelId("GlidaLee_1");
            builder.build();
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(001,builder.build());
        }else if(BFlag == false && GFlag == false){
            Toast.makeText(UserShoppingCartActivity.this,"Order Failed !",Toast.LENGTH_SHORT).show();
        }
    }
}