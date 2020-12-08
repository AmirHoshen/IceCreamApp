package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class goldaActivityMenu extends AppCompatActivity {

    RecyclerView goldaRecyclerView;

    String s1[], s2[];
    int images[] = {R.drawable.goldaloacker,
            R.drawable.goldacream,
            R.drawable.goldacoockiman,
            R.drawable.goldachocolateKaramel,
            R.drawable.goldatropical,
            R.drawable.goldasugarFree,
            R.drawable.goldasorbe,
            R.drawable.goldarekotMaskarpon,
            R.drawable.goldaveganOreoMuss,
            R.drawable.goldavegan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golda_menu);

        goldaRecyclerView = findViewById(R.id.recyclerViewGolda);

        s1 = getResources().getStringArray(R.array.golda_ice_cream_tastes);
        s2 = getResources().getStringArray(R.array.golda_ice_cream_description);

        RecyclerViewAdapter recViewAdapter = new RecyclerViewAdapter(this, s1, s2, images);

        goldaRecyclerView.setAdapter(recViewAdapter);
        goldaRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}