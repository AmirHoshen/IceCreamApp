package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BusinessOlderOrdersActivity extends AppCompatActivity {

    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_older_orders);

        backPress = findViewById(R.id.busOlderOrderBackPressBtn);
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessOlderOrdersActivity.this, BusinessArea.class));
                finish();
            }
        });
    }
}