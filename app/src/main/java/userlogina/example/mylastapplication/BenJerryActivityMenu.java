package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class BenJerryActivityMenu extends AppCompatActivity {

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

        benJerry = findViewById(R.id.benjerryRecyclerView);

        s1 = getResources().getStringArray(R.array.ben_jerry_ice_cream_tastes);
        s2 = getResources().getStringArray(R.array.ben_jerry_ice_cream_description);

        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, s1, s2, images);

        benJerry.setAdapter(recViewAdapter);
        benJerry.setLayoutManager(new LinearLayoutManager(this));

    }
}