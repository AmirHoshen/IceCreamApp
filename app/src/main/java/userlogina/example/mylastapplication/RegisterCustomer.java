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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import static userlogina.example.mylastapplication.MainActivityUserReg.progressBarUsr;

public class RegisterCustomer extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText editTextFullName, editTextEmail, editTextPhone, editTextPassword;
    private ProgressBar progressBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView)findViewById(R.id.bannerRegCustomer);
        banner.setOnClickListener(this);

        registerUser = (Button)findViewById(R.id.buttonRegNewCustomer);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText)findViewById(R.id.editTextTextPersonNameRegCustomer);
        editTextEmail = (EditText)findViewById(R.id.editTextTextEmailAddressRegCus);
        editTextPhone = (EditText)findViewById(R.id.editTextPhoneRegCus);
        editTextPassword = (EditText)findViewById(R.id.editTextTextPasswordRegCus);

        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        progressBarUsr.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bannerRegCustomer:
                startActivity(new Intent(this, MainActivityUserReg.class));
                break;
            case R.id.buttonRegNewCustomer:
                registerUser();
        }
    }

    private void registerUser() {
        String fullName, email, phone, password;

        fullName = editTextFullName.getText().toString().trim();
        email =  editTextEmail.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        progressBar2.setVisibility(View.VISIBLE);
        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required!");
            editTextPhone.requestFocus();
            return;
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

        progressBar2.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(fullName, email, phone, password);

                    //return the id for the registered user
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fullName).build();

                                _user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            
                                        }
                                    }
                                });
                                Toast.makeText(RegisterCustomer.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                progressBar2.setVisibility(View.GONE);
                                startActivity(new Intent(RegisterCustomer.this, MainActivityCustomerLandPage.class));
                                finish();
                            }else{
                                Toast.makeText(RegisterCustomer.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                progressBar2.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterCustomer.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);
                }
            }
        });

    }
}