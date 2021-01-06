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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static userlogina.example.mylastapplication.MainActivity.progressBar;

public class UserRegMainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseCustomer;
    TextView registerNewUser, forgotPassword , backToMainBanner;
    EditText editTextEmail, editTextPassword;
    Button loginUsr;
    public static ProgressBar progressBarUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg_main);

        mAuth = FirebaseAuth.getInstance();

        databaseCustomer = FirebaseDatabase.getInstance().getReference().child("Users");

        registerNewUser = (TextView) findViewById(R.id.textViewRegisterUser);

        forgotPassword = (TextView) findViewById(R.id.forgotPasswordBtnUser);

        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);

        editTextPassword = (EditText) findViewById(R.id.editTextNumberPassword);

        loginUsr = (Button) findViewById(R.id.buttonLoginUserReg);

        backToMainBanner = (TextView)findViewById(R.id.txtViewLogoMain1);

        progressBarUsr = (ProgressBar) findViewById(R.id.progressBar1);
//        progressBar.setVisibility(View.INVISIBLE);

        backToMainBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegMainActivity.this, MainActivity.class));
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            if(user.getUid().equals(FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).getRef().getKey())){
                startActivity(new Intent(UserRegMainActivity.this, UserLandPageMainActivity.class));}

            registerNewUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBarUsr.setVisibility(View.VISIBLE);
                    startActivity(new Intent(UserRegMainActivity.this, UserRegister.class));
                }
            });

            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    forgotPassword();
                }
            });

            loginUsr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBarUsr.setVisibility(View.VISIBLE);
                    login();

                }
            });


    }

    private void forgotPassword() {
        startActivity(new Intent(UserRegMainActivity.this, ForgotPasswordPageActivity.class));
    }

    private void login() {
        String email, password;

        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Min Password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        progressBarUsr.setVisibility(View.VISIBLE);
        Query query = databaseCustomer.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {//problem no restriction of Business or Customer both login!
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserRegMainActivity.this, "Welcome " + email.substring(0, email.lastIndexOf("@")) + "!", Toast.LENGTH_LONG).show();
                                progressBarUsr.setVisibility(View.GONE);
                                startActivity(new Intent(UserRegMainActivity.this, UserLandPageMainActivity.class));
                            } else {
                                Toast.makeText(UserRegMainActivity.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                                progressBarUsr.setVisibility(View.GONE);
                            }
                        }
                    });
                }else {
                    editTextPassword.setText(null);
                    Toast.makeText(UserRegMainActivity.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                    progressBarUsr.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserRegMainActivity.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                progressBarUsr.setVisibility(View.GONE);
                throw error.toException();
            }
        });


    }
}