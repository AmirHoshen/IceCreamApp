package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BusinessWatchMenuActivity extends AppCompatActivity {

    private ImageView backPressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_watch_menu);

        backPressBtn = findViewById(R.id.busOlderOrderBackPressBtn);
        backPressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessWatchMenuActivity.this, BusinessArea.class));
                finish();
            }
        });
    }
}