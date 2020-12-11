package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static userlogina.example.mylastapplication.UserMainActivityLogin.progressBarUsr;

public class UserRegister extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private TextView banner;
    private EditText editTextFullName, editTextEmail, editTextPhone, editTextPassword;

    private Button registerUserBtn;

    private static final String USER = "user";
    private static final String TAG = "RegistrationActivity";

    private User user;

    private ProgressBar progressBar2;

    /**
     * Creating the registration page for each customer registration in the app
     * view items from this page collected from page context and then inserted
     * into firebase creating a new user branch in firebase database.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.bannerRegCustomer);
        banner.setOnClickListener(this);

        registerUserBtn = (Button) findViewById(R.id.buttonRegNewCustomer);

        editTextFullName = (EditText) findViewById(R.id.editTextTextPersonNameRegCustomer);
        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddressRegCus);
        editTextPhone = (EditText) findViewById(R.id.editTextPhoneRegCus);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPasswordRegCus);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBarUsr.setVisibility(View.INVISIBLE);

        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = editTextFullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String password = editTextPassword.getText().toString();


                if (fullName.isEmpty()) {
                    editTextFullName.setError("Full name is required!");
                    editTextFullName.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    editTextPhone.setError("Phone number is required!");
                    editTextPhone.requestFocus();
                    return;
                }
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
                    editTextPassword.setError("Password length should be 6 characters or more!");
                    editTextPassword.requestFocus();
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);


                user = new User(fullName, email, phone, password);

                registerUser(email, password);

            }
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(UserRegister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    public void updateUI(FirebaseUser currentUsser) {

        //insert user information to firebase
        String keyID = mDatabase.push().getKey();
        mDatabase.child(keyID).setValue(user);
        Intent loginIntent = new Intent(this, UserMainActivityLogin.class);
        startActivity(loginIntent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bannerRegCustomer:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    //case R.id.buttonRegNewCustomer:
    //registerUser();
//    private void registerUser() {
//        String fullName, email, phone, password;
//
//        fullName = editTextFullName.getText().toString().trim();
//        email = editTextEmail.getText().toString().trim();
//        phone = editTextPhone.getText().toString().trim();
//        password = editTextPassword.getText().toString().trim();
//
//        progressBar2.setVisibility(View.VISIBLE);
//

}
