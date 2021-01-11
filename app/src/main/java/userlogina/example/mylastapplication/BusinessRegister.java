package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import userlogina.example.mylastapplication.Orders.Dish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static userlogina.example.mylastapplication.R.array.Cities;
import static userlogina.example.mylastapplication.MainActivity.progressBar;
import static userlogina.example.mylastapplication.BusinessRegMainActivity.progressBarBsns;

public class BusinessRegister extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private TextView banner, registerBusiness;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //   DatabaseReference myRef = database.getReference();

    private EditText editTextBusinessOwnFullName, editTextBusinessName,editTextEmail, editTextPhone, editTextPassword;
    private String city;
    private CheckBox iceCreamOwner_golda;
    private CheckBox iceCreamOwner_BJ;
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

        iceCreamOwner_golda = findViewById(R.id.checkBoxBusniessGolda);
        iceCreamOwner_BJ = findViewById(R.id.checkBoxBusniessBJ);


        iceCreamOwner_golda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iceCreamOwner_golda.isChecked()) {
                    iceCreamOwner_BJ.setChecked(false);
                }
                if (iceCreamOwner_BJ.isChecked()) {
                    iceCreamOwner_golda.setChecked(false);
                }
            }
        });

        iceCreamOwner_BJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iceCreamOwner_BJ.isChecked()) {
                    iceCreamOwner_golda.setChecked(false);
                }
                if (iceCreamOwner_golda.isChecked()) {
                    iceCreamOwner_BJ.setChecked(false);
                }
            }
        });

        //Cities Spinner
        Spinner Cities_spinner = findViewById(R.id.spinnerBusinessLocation);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, Cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cities_spinner.setAdapter(adapter);
        Cities_spinner.setOnItemSelectedListener(this);

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
        final String type;
        if (iceCreamOwner_golda.isChecked()) {
            type = "Golda";
        }
        else{
            type = "BJ";
        }

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
                    Business b = new Business(businessOwnerFullName, businessName, email, phone, password, city, type);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), city, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

