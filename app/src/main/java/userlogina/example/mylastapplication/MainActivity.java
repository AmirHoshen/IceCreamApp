package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button customer, businessOwner;
    public static ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private boolean wasUser;
    private boolean notExist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        customer = findViewById(R.id.buttonCustomerMain);
        businessOwner = findViewById(R.id.buttonBusinessOwnMain);
        progressBar = findViewById(R.id.progressBar);

        //main screen for customer

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts a new intent activity.
                mAuth = FirebaseAuth.getInstance();
                if (wasUser) {
                    progressBar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(MainActivity.this, UserLandPageMainActivity.class));
                    finish();
                } else {
                    openCustomerLoginActivity();
                }
            }
        });

        businessOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts a new intent activity.
                if (!wasUser && !notExist) {
                    progressBar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(MainActivity.this, BusinessArea.class));
                    finish();
                } else {
                    openBusinessLoginActivity();
                }
            }
        });

    }

    private void openCustomerLoginActivity() {
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(MainActivity.this, UserRegMainActivity.class));
        finish();
    }

    private void openBusinessLoginActivity() {
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(MainActivity.this, BusinessRegMainActivity.class));
        finish();

    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            notExist = false;
            FirebaseAuth.AuthStateListener mAuthListener;
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Query isUser = FirebaseDatabase.getInstance().getReference().child("Users").equalTo(user.getUid());
                    isUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (user != null && snapshot.exists()) {
                                wasUser = true;
                            } else if (!snapshot.exists()) {
                                wasUser = false;
                                notExist = true;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            };
        } else {
            notExist = true;
        }
    }
}