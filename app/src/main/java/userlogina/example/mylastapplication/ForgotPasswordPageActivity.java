package userlogina.example.mylastapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPageActivity extends AppCompatActivity {
    private Button resetPassButton;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        resetPassButton = (Button) findViewById(R.id.ResetPasswordButton);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);//change name for this field!
        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }

            private void resetPass() {
                String emailReset;
                emailReset = email.getText().toString().trim();

                if(emailReset.isEmpty()) {
                    email.setError("Email is required!");
                    email.requestFocus();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailReset).matches()) {
                    email.setError("Please provide a valid email!");
                    email.requestFocus();
                    return;
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(emailReset)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordPageActivity.this,"Email For Reset Password Sent!",Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    email.setText(null);
                                    Toast.makeText(ForgotPasswordPageActivity.this,"Email not Found! Try again",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}