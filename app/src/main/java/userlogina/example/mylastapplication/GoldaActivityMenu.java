package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GoldaActivityMenu extends AppCompatActivity {

    Button previousPageGoldaBtn;
    RecyclerView goldaRecyclerView;

    String s1[], s2[];
    int images[] = {R.drawable.goldaloacker,
            R.drawable.goldacream,
            R.drawable.goldacoockiman,
            R.drawable.goldachocolatekaramel,
            R.drawable.goldatropical,
            R.drawable.goldasugarfree,
            R.drawable.goldasorbe,
            R.drawable.goldarekotmaskarpon,
            R.drawable.goldaveganoreomuss,
            R.drawable.goldavegan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golda_menu);

        goldaRecyclerView = findViewById(R.id.recyclerViewGolda);
        previousPageGoldaBtn = findViewById(R.id.previousPageGoldaBtn);

        previousPageGoldaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        s1 = getResources().getStringArray(R.array.golda_ice_cream_tastes);
        s2 = getResources().getStringArray(R.array.golda_ice_cream_description);

        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, s1, s2, images);

        goldaRecyclerView.setAdapter(recViewAdapter);
        goldaRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}