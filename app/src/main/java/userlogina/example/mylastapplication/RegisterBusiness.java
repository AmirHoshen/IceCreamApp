package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterBusiness extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner, registerBusiness;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();

    private EditText editTextBusinessOwnFullName, editTextBusinessName,editTextEmail, editTextPhone, editTextPassword;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_business);

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

        progressBar = (ProgressBar)findViewById(R.id.progressBar3);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bannerRegBusiness:
                startActivity(new Intent(this, MainActivityUserReg.class));
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

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Business b = new Business(businessOwnerFullName, businessName,email, phone, password);

                    //return the id for the registered user
                    FirebaseDatabase.getInstance().getReference("Business")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(b).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(RegisterBusiness.this, "Business owner has been registered successfully", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(RegisterBusiness.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterBusiness.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}