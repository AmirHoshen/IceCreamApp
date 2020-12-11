package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button customer, businessOwner;
    public static ProgressBar progressBar;
    private FirebaseAuth auth;

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
                progressBar.setVisibility(View.VISIBLE);
                openCustomerLoginActivity();

            }
        });

        businessOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts a new intent activity.
                progressBar.setVisibility(View.VISIBLE);
                openBusinessLoginActivity();
            }
        });

    }

    private void openCustomerLoginActivity() {
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(MainActivity.this, UserMainActivityLogin.class));
    }

    private void openBusinessLoginActivity(){
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(MainActivity.this, BusinessMainActivityLogin.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //happens if the customer gets back to main menu so it will sign out and will have to start all over again
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            FirebaseAuth.getInstance().signOut();
    }
}