package userlogina.example.mylastapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivityCustomerLandPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer_land_page);

        TabLayout tabLayout = findViewById(R.id.tabBarCustomer);
        TabItem tabProfile = findViewById(R.id.tabProfile);
        TabItem tabOrderNow = findViewById(R.id.tabOrderNow);
        TabItem tabOrderHistory = findViewById(R.id.tabOrderHistory);
        ViewPager viewPager = findViewById(R.id.viewPagerCustomer);


    }
}