package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import userlogina.example.mylastapplication.Orders.Dish;
import userlogina.example.mylastapplication.Orders.Order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static userlogina.example.mylastapplication.MainActivity.progressBar;
import static userlogina.example.mylastapplication.BusinessRegMainActivity.progressBarBsns;

public class BusinessRegister extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner, registerBusiness;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //   DatabaseReference myRef = database.getReference();

    private EditText editTextBusinessOwnFullName, editTextBusinessName,editTextEmail, editTextPhone, editTextPassword;
    public static ProgressBar progressBar3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView)findViewById(R.id.bannerRegBusiness);
        banner.setOnClickListener(this);

        registerBusiness = (Button)findViewById(R.id.buttonRegNewBusiness);
        registerBusiness.setOnClickListener(this);

        editTextBusinessOwnFullName = (EditText)findViewById(R.id.editTextBusinessOwnerName);
        editTextBusinessName = (EditText)findViewById(R.id.editTextBusinessName);
        editTextEmail = (EditText)findViewById(R.id.editTextBusinessEmailAddress);
        editTextPhone = (EditText)findViewById(R.id.editTextBusinessPhone);
        editTextPassword = (EditText)findViewById(R.id.editTextBusinessPassword);
        progressBar.setVisibility(View.INVISIBLE);
        progressBarBsns.setVisibility(View.INVISIBLE);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bannerRegBusiness:
                startActivity(new Intent(this, UserRegMainActivity.class));
                break;
            case R.id.buttonRegNewBusiness:
                registerUser();
        }
    }

    private void registerUser() {
        String businessOwnerFullName, businessName,email, phone, password;

        businessOwnerFullName = editTextBusinessOwnFullName.getText().toString().trim();
        businessName = editTextBusinessName.getText().toString().trim();
        email =  editTextEmail.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        ArrayList<Order> orders = new ArrayList<Order>();
        ArrayList<Dish> order = new ArrayList<Dish>();
        order.add(new Dish("vanila","Tasty Shit",2,1));
        order.add(new Dish("caramel","Goodies",15,2));
        orders.add(new Order("1",businessName,"0000000000","makom 22/2", order,32.0,"ok","08/12/2020 21:42:01"));


        if(businessOwnerFullName.isEmpty()){
            editTextBusinessOwnFullName.setError("Full name is required!");
            editTextBusinessOwnFullName.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required!");
            editTextPhone.requestFocus();
            return;
        }
        if(businessName.isEmpty()){
            editTextBusinessName.setError("Business name is required!");
            editTextBusinessName.requestFocus();
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Min Password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar3.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Business b = new Business(businessOwnerFullName, businessName, email, phone, password,orders);

                    //return the id for the registered user
                    FirebaseDatabase.getInstance().getReference("Business")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(b).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(BusinessRegister.this, "Business owner has been registered successfully", Toast.LENGTH_LONG).show();
                                progressBar3.setVisibility(View.GONE);
                                finish();
                            }else{
                                Toast.makeText(BusinessRegister.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                progressBar3.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(BusinessRegister.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                    progressBar3.setVisibility(View.GONE);
                }
            }
        });

    }
}