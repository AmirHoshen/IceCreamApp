package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView TextViewPSOwnerName;
    private TextView TextViewPSBusinessEmail;
    private TextView TextViewPSBusinessEmailPassword;
    private TextView TextViewPSBusinessLocation;
    private TextView TextViewPSBusinessName;
    private FirebaseUser business = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference ref_business;
    private Button buttonLogout;
    private Button buttonReturn;
    private Business curr_Business;

    String DBKey;
    String currentPassword;

    // for popup of location
    Dialog LocationDialog;
    private EditText EditTextPopUpLocation;
    private Button buttonPopUPLocationChange;

    // for popup of business name
    Dialog BusinessNameDialog;
    private EditText EditTextPopUpBusName;
    private Button buttonPopUPBusNameChange;

    // for popup of business name
    Dialog BusinessPasswordDialog;
    private EditText EditTextPopUpPasswordCurr;
    private EditText EditTextPopUpPasswordNew;
    private Button buttonPopUPasswordChange;

    public PersonalSettingsActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);


        ref_business = FirebaseDatabase.getInstance().getReference("Business");
        TextViewPSOwnerName = findViewById(R.id.TextViewPSOwnerName);
        TextViewPSOwnerName.setOnClickListener(this);
        TextViewPSBusinessName = findViewById(R.id.TextViewPSBusinessName);
        TextViewPSBusinessName.setOnClickListener(this);
        TextViewPSBusinessEmail = findViewById(R.id.TextViewPSBusinessEmail);
        TextViewPSBusinessEmail.setOnClickListener(this);
        TextViewPSBusinessEmailPassword = findViewById(R.id.TextViewPSBusinessEmailPassword);
        TextViewPSBusinessEmailPassword.setOnClickListener(this);
        TextViewPSBusinessLocation = findViewById(R.id.TextViewPSBusinessLocation);
        TextViewPSBusinessLocation.setOnClickListener(this);
        buttonLogout = (Button) findViewById(R.id.Logout);
        buttonLogout.setOnClickListener(this);
        buttonReturn = (Button) findViewById(R.id.buttonReturnPS);
        buttonReturn.setOnClickListener(this);
        ref_business.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                curr_Business = dataSnapshot.child(business.getUid()).getValue(Business.class);
                TextViewPSOwnerName.setText("Business Owner Name: " + curr_Business.getBusinessOwnerName());
                TextViewPSBusinessName.setText("Business Name: " + curr_Business.getBusinessName());
                TextViewPSBusinessEmail.setText("Email: " + curr_Business.getEmail());
                TextViewPSBusinessEmailPassword.setText("Password: " + curr_Business.getPassword());
                TextViewPSBusinessLocation.setText("Location: " + curr_Business.getLocation());
                final String currentPassword = curr_Business.getPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view == TextViewPSBusinessLocation){
            DBKey = "location";
            CreatePopupLocation();
        } else if(view == buttonPopUPLocationChange ){
            updateInDatabase(DBKey);
            Toast.makeText(this, "Location update successful", Toast.LENGTH_LONG).show();
            LocationDialog.dismiss();
        }

        if(view == TextViewPSBusinessName){
            DBKey = "businessName";
            CreatePopupBusinessName();
        } else if(view == buttonPopUPBusNameChange ){
            updateInDatabase(DBKey);
            Toast.makeText(this, "Business Name update successful", Toast.LENGTH_LONG).show();
            BusinessNameDialog.dismiss();
        }

        if(view == TextViewPSBusinessEmailPassword){
            DBKey = "password";
            CreatePopupPassword();
        } else if(view == buttonPopUPasswordChange ){
            String password_1 = EditTextPopUpPasswordCurr.getText().toString().trim();
            if (password_1.equals(curr_Business.getPassword())) {
                updateInDatabase(DBKey);
                Toast.makeText(this, "Password update successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "The current password does not match", Toast.LENGTH_LONG).show();
                return;
            }
            BusinessPasswordDialog.dismiss();
        }

        if(view == TextViewPSOwnerName){
            Toast.makeText(this, "You can not edit this figure", Toast.LENGTH_LONG).show();
        }

        if(view == TextViewPSBusinessEmail){
            Toast.makeText(this, "You can not edit this figure", Toast.LENGTH_LONG).show();
        }

        if(view == buttonLogout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(PersonalSettingsActivity.this, MainActivity.class));
            finish();
        }

        if(view == buttonReturn){
            startActivity(new Intent(PersonalSettingsActivity.this, BusinessArea.class));
            finish();

        }

    }

    private void CreatePopupLocation() {
        LocationDialog = new Dialog(this);
        LocationDialog.setContentView(R.layout.popup_business_location_dialog);
        LocationDialog.setTitle("Update Location");
        LocationDialog.setCancelable(true);
        EditTextPopUpLocation = (EditText) LocationDialog.findViewById(R.id.EditTextPopUpBName);
        buttonPopUPLocationChange = (Button)  LocationDialog.findViewById(R.id.buttonPopUpBNameChange);
        buttonPopUPLocationChange.setOnClickListener(this);
        LocationDialog.show();
    }

    private void CreatePopupBusinessName() {
        BusinessNameDialog = new Dialog(this);
        BusinessNameDialog.setContentView(R.layout.popup_business_name);
        BusinessNameDialog.setTitle("Update Business Name");
        BusinessNameDialog.setCancelable(true);
        EditTextPopUpBusName = (EditText) BusinessNameDialog.findViewById(R.id.EditTextPopUpBName);
        buttonPopUPBusNameChange = (Button)  BusinessNameDialog.findViewById(R.id.buttonPopUpBNameChange);
        buttonPopUPBusNameChange.setOnClickListener(this);
        BusinessNameDialog.show();
    }

    private void CreatePopupPassword() {
        BusinessPasswordDialog = new Dialog(this);
        BusinessPasswordDialog.setContentView(R.layout.popup_business_password_dialog);
        BusinessPasswordDialog.setTitle("Update Password");
        BusinessPasswordDialog.setCancelable(true);
        EditTextPopUpPasswordCurr = (EditText) BusinessPasswordDialog.findViewById(R.id.EditTextPopUpPasswordCurr);
        EditTextPopUpPasswordNew = (EditText) BusinessPasswordDialog.findViewById(R.id.EditTextPopUpPasswordNew);
        buttonPopUPasswordChange = (Button)  BusinessPasswordDialog.findViewById(R.id.buttonPopUPasswordChange);
        buttonPopUPasswordChange.setOnClickListener(this);
        BusinessPasswordDialog.show();
    }

    private void updateInDatabase(String choice) {
        ref_business.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String businessName, location, passwordNew, passwordOlder;
                if (DBKey.equals("location")) {
                    location = EditTextPopUpLocation.getText().toString().trim();
                    ref_business.child(business.getUid()).child("location").setValue(location);
                }
               else if (DBKey.equals("businessName")) {
                   businessName = EditTextPopUpBusName.getText().toString().trim();
                    ref_business.child(business.getUid()).child("businessName").setValue(businessName);;
               }
               else if(DBKey.equals("password")){
                       passwordNew = EditTextPopUpPasswordNew.getText().toString().trim();
                       ref_business.child(business.getUid()).child("password").setValue(passwordNew);;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

}






