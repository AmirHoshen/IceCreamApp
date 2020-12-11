package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    Button customer, businessOwner;
    public static ProgressBar progressBar;

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

                openCustomerLoginActivity();

            }
        });

        businessOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts a new intent activity.

                openBusinessLoginActivity();
            }
        });

    }

    private void openCustomerLoginActivity() {
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(MainActivity.this, MainActivityUserReg.class));
    }

    private void openBusinessLoginActivity(){
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(MainActivity.this, MainActivityBusinessReg.class));
    }
}