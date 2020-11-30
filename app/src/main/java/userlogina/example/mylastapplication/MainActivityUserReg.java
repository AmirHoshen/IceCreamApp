package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivityUserReg extends AppCompatActivity {

   TextView registerNewUser;
   EditText editTextEmail, editTextPassword;
   Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reg_user);

        registerNewUser = (TextView)findViewById(R.id.textViewRegisterUser);

        editTextEmail = (EditText)findViewById(R.id.editTextTextEmailAddress);

        editTextPassword = (EditText)findViewById(R.id.editTextNumberPassword);

        login = (Button)findViewById(R.id.buttonLoginUserReg);


        registerNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityUserReg.this, RegisterCustomer.class));
            }
        });

    }


}