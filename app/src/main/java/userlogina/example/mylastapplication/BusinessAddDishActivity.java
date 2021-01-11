package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import userlogina.example.mylastapplication.Orders.Upload;
//import userlogina.example.mylastapplication.Orders.Dish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class BusinessAddDishActivity extends AppCompatActivity {

    private FirebaseAuth m;
    private StorageReference mStorageRef;
    private DatabaseReference dbRef;
    private FirebaseUser business;
    private EditText dishName,dishPrice,dishDescription;
    private Button addDishButton,uploadImageButton;
    private ImageView businessBackPressBtn,uploadImage;
    public Uri imageUri;
    public String randomUID = UUID.randomUUID().toString();
    public String path = "images/"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"/"+ randomUID;


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
        uploadImageButton = findViewById(R.id.UploadButton);
        businessBackPressBtn = findViewById(R.id.busOlderOrderBackPressBtn);
        uploadImage = findViewById(R.id.dishImagesUp);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        businessBackPressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessAddDishActivity.this, BusinessArea.class));
                finish();
            }
        });

        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addDish();
                    startActivity(new Intent(BusinessAddDishActivity.this, BusinessArea.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()!=null){
            imageUri = data.getData();
            uploadImage.setImageURI(imageUri);
        }
    }

    private void addDish() throws InterruptedException {
        uploadImage();
        String _dishName,_dishDescription;
        String _imageUri;
        Double _dishPrice;
        _dishName = dishName.getText().toString();
        _dishDescription = dishDescription.getText().toString();
        _imageUri = path;

        if (_imageUri == null || _imageUri.isEmpty()) {
            Toast.makeText(BusinessAddDishActivity.this,"Failed To Add Dish!\nUri Error!",Toast.LENGTH_LONG).show();
            return;
        }
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
        Upload dish = new Upload(_dishName, _dishDescription, _dishPrice,_imageUri,""+FirebaseAuth.getInstance().getCurrentUser().getEmail());


        dbRef.push().setValue(dish).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dishPrice.setText(null);
                dishDescription.setText(null);
                dishName.setText(null);
                uploadImage.setImageURI(null);
                Toast.makeText(BusinessAddDishActivity.this,"Dish Added!",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BusinessAddDishActivity.this,"Failed To Add Dish!",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void uploadImage() {
        mStorageRef =  mStorageRef.child(path);
        UploadTask uploadTask = mStorageRef.putFile(imageUri);


        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return mStorageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    Uri downloadUri = task.getResult();
                    imageUri = downloadUri;
                    System.out.println("Upload " + downloadUri);
                    Toast.makeText(BusinessAddDishActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BusinessAddDishActivity.this, "upload Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}