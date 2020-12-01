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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static userlogina.example.mylastapplication.MainActivity.progressBar;

public class MainActivityBusinessReg extends AppCompatActivity {

    private EditText emailBusiness, passwordBusiness;
    public static ProgressBar progressBarBsns;
    private TextView registerBusiness;
    private Button loginBtnBusiness;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reg_bus);
        mAuth = FirebaseAuth.getInstance();
        emailBusiness = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordBusiness = (EditText) findViewById(R.id.editTextNumberPassword);
        progressBarBsns = (ProgressBar) findViewById(R.id.progressBar1);
        registerBusiness = (TextView) findViewById(R.id.textViewRegisterBusinessMainRegBus);
        loginBtnBusiness = (Button) findViewById(R.id.buttonLoginBusinessReg);
        databaseBusiness = FirebaseDatabase.getInstance().getReference().child("Business");
        progressBar.setVisibility(View.INVISIBLE);
        registerBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.textViewRegisterBusinessMainRegBus:
                        progressBarBsns.setVisibility(View.VISIBLE);
                        startActivity(new Intent(MainActivityBusinessReg.this, RegisterBusiness.class));
                        break;
                }
            }
        });
        loginBtnBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                progressBarBsns.setVisibility(View.VISIBLE);
            }
        });
    }

    private void login() {
        String email, password;

        email = emailBusiness.getText().toString().trim();
        password = passwordBusiness.getText().toString().trim();

        if (email.isEmpty()) {
            emailBusiness.setError("Email is required!");
            emailBusiness.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailBusiness.setError("Please provide a valid email!");
            emailBusiness.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordBusiness.setError("Password is required!");
            passwordBusiness.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordBusiness.setError("Min Password length should be 6 characters!");
            passwordBusiness.requestFocus();
            return;
        }
        progressBarBsns.setVisibility(View.VISIBLE);
        Query query = databaseBusiness.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {//problem no restriction of Business or Customer both login!
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivityBusinessReg.this, "Welcome " + email.substring(0, email.lastIndexOf("@")) + "!", Toast.LENGTH_LONG).show();
                                progressBarBsns.setVisibility(View.GONE);
                                startActivity(new Intent(MainActivityBusinessReg.this, Business_Area.class));
                            } else {
                                Toast.makeText(MainActivityBusinessReg.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                                progressBarBsns.setVisibility(View.GONE);
                            }
                        }
                    });
                }else {
                    passwordBusiness.setText(null);
                    Toast.makeText(MainActivityBusinessReg.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                    progressBarBsns.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivityBusinessReg.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                progressBarBsns.setVisibility(View.GONE);
                throw error.toException();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
    }
}