package userlogina.example.mylastapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ShoppingArea extends AppCompatActivity {


    private Button golda, bnj, user_menu, main_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_area);

        golda     = (Button) findViewById(R.id.ShoppingAreaGolda);
        bnj       = (Button) findViewById(R.id.ShoppingAreaBnJ);
        user_menu = (Button) findViewById(R.id.ShoppingAreaUserMenu);
        main_menu = (Button) findViewById(R.id.ShoppingAreaMainMenu);

        golda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingArea.this, MainActivity.class));
            }
        });

        bnj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingArea.this, MainActivity.class));
            }
        });

        user_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingArea.this, MainActivityUserReg.class));
            }
        });

        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingArea.this, MainActivity.class));
            }
        });
    }
}