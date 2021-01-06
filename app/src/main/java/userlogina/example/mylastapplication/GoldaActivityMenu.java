package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import userlogina.example.mylastapplication.Orders.Dish;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class GoldaActivityMenu extends AppCompatActivity {

    Button previousPageGoldaBtn, shoppingCartGolda;
    RecyclerView goldaRecyclerView;

    ArrayList<Dish> dishes = new ArrayList<>();
    ArrayList<String> _name = new ArrayList<>();
    ArrayList<String> _description = new ArrayList<>();
    ArrayList<Double> _price = new ArrayList<>();
    ArrayList<Bitmap> _images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golda_menu);

        goldaRecyclerView = findViewById(R.id.recyclerViewGolda);
        previousPageGoldaBtn = findViewById(R.id.previousPageGoldaBtn);
        shoppingCartGolda = findViewById(R.id.shoppingCartGolda);

        previousPageGoldaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shoppingCartGolda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoldaActivityMenu.this, UserShoppingCartActivity.class));
                finish();
            }
        });

        //query to put info in s1,...,s3
         //Query dishList = FirebaseDatabase.getInstance().getReference("Business").child("OZz7TYO50lQdvGUTkTaXJemSjro2").child("Dishes").getRef();
        Collector();



        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, _name, _description, _price, null);

        goldaRecyclerView.setAdapter(recViewAdapter);
        goldaRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    public void Collector(){
        Query dishList = FirebaseDatabase.getInstance().getReference("Business").child("OZz7TYO50lQdvGUTkTaXJemSjro2").child("Dishes").getRef();
        dishList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        System.out.println("Dish located!");
                        Dish d = dataSnapshot.getValue(Dish.class);
                        dishes.add(d);
                    }
                else
                    System.out.println("Not geeting Query Right!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for (Dish d: dishes
        ) {
            _name.add(d.getFalvor());
            _description.add(d.getDescription());
            _price.add(d.getPrice());
            FirebaseStorage.getInstance().getReference().child(d.getImageUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(GoldaActivityMenu.this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    _images.add(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(GoldaActivityMenu.this,"Image Getting Error!",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public Bitmap getBitmapFromURI(Uri uri){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = GoldaActivityMenu.this.getContentResolver().query(uri, filePathColumn, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            File f_image = new File(cursor.getString(columnIndex));
            cursor.close();
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            return BitmapFactory.decodeFile(f_image.getAbsolutePath(), o2);
        }
        return null;
    }
}