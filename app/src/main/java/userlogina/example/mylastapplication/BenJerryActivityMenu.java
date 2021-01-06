package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BenJerryActivityMenu extends AppCompatActivity {

    Button previousPageBenJerryBtn, shoppingCartBJ;
    RecyclerView benJerry;

    ArrayList<Dish> dishList = new ArrayList<>();
    ArrayList<String> _name = new ArrayList<>();
    ArrayList<String> _description = new ArrayList<>();
    ArrayList<Bitmap> _images = new ArrayList<>();
    ArrayList<Double> _price = new ArrayList<>();


    Query Dishes = FirebaseDatabase.getInstance().getReference().child("Business").child("HztqosDImpb5hO7d7XF7ev2l7152").child("Dishes").getRef();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ben_jerry_menu);



        previousPageBenJerryBtn = (Button)findViewById(R.id.previousPageBenJerryBtn);
        shoppingCartBJ = (Button)findViewById(R.id.shoppingCartBenJerryBtn);

        previousPageBenJerryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shoppingCartBJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BenJerryActivityMenu.this, UserShoppingCartActivity.class));
                finish();
            }
        });
        Collector();

        benJerry = findViewById(R.id.benjerryRecyclerView);
        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, _name, _description,_price, _images);

        benJerry.setAdapter(recViewAdapter);
        benJerry.setLayoutManager(new LinearLayoutManager(this));

    }
    public Bitmap getBitmapFromURI(Uri uri){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = BenJerryActivityMenu.this.getContentResolver().query(uri, filePathColumn, null, null, null);
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
    public void Collector(){
        Dishes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println(snapshot.getKey());
                if(snapshot.exists())
                    for(DataSnapshot snap: snapshot.getChildren()){
                        System.out.println("Dish located!");
                        Dish dish = snap.getValue(Dish.class);
                        dishList.add(dish);
                        System.out.println(dish.getFalvor());
                    }
                else
                    System.out.println("Not geeting Query Right!");
                Toast.makeText(BenJerryActivityMenu.this,"Dishes Getting!",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        for(Dish dish: dishList){
            System.out.println(dish.getFalvor());
            _name.add(dish.getFalvor());
            _description.add(dish.getDescription());
            FirebaseStorage.getInstance().getReference().child(dish.getImageUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(BenJerryActivityMenu.this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    _images.add(bitmap);

                    Toast.makeText(BenJerryActivityMenu.this,"Image Getting!",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(BenJerryActivityMenu.this,"Image Getting Error!",Toast.LENGTH_LONG).show();
                }
            });
            _price.add(dish.getPrice());

        }
    }



}