package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BenJerryActivityMenu extends AppCompatActivity {

    Button previousPageBenJerryBtn;
    RecyclerView benJerry;
    String s1[], s2[];

    int images[] = {R.drawable.bjcinnamonbuns,
            R.drawable.bjbraunychocolate,
            R.drawable.bjbraunycaramel,
            R.drawable.bjboomchocolate,
            R.drawable.bjpinutbuttercoockievegan,
            R.drawable.bjkaramelsutra,
            R.drawable.bjfishfood,
            R.drawable.bjcoockiedowchocchips,
            R.drawable.bjwakenobake,
            R.drawable.bjstrawberrysorbe};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ben_jerry_menu);

        previousPageBenJerryBtn = (Button)findViewById(R.id.previousPageBenJerryBtn);

        previousPageBenJerryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        benJerry = findViewById(R.id.benjerryRecyclerView);

        s1 = getResources().getStringArray(R.array.ben_jerry_ice_cream_tastes);
        s2 = getResources().getStringArray(R.array.ben_jerry_ice_cream_description);

        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, s1, s2, images);

        benJerry.setAdapter(recViewAdapter);
        benJerry.setLayoutManager(new LinearLayoutManager(this));

    }



}