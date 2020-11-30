package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivityBusinessReg extends AppCompatActivity {

    private TextView  registerBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reg_bus);

        registerBusiness = (TextView)findViewById(R.id.textViewRegisterBusinessMainRegBus);
        registerBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.textViewRegisterBusinessMainRegBus:
                        startActivity(new Intent(MainActivityBusinessReg.this, RegisterBusiness.class));
                        break;
                }
            }
        });
    }
}