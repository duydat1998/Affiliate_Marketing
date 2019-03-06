package swd.affiliate_marketing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import swd.affiliate_marketing.model.Campaign;

public class MainActivity extends AppCompatActivity {


    private Campaign currentCampaign = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_campaign:
                    loadFragment(new CampaignFragment());
                    return true;
                case R.id.navigation_report:
                    loadFragment(new ReportFragment());
                    return true;
                case R.id.navigation_profile:
                    loadFragment(new ProfileFragment());
                    return true;
                case R.id.navigation_notification:
                    loadFragment(new NotificationFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new CampaignFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void openCampaignDetailFragment(Campaign campaign){
        CampaignDetailFragment detailFragment = new CampaignDetailFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .commit();
        currentCampaign = campaign;
    }

    public Campaign getCurrentCampaign(){
        return currentCampaign;
    }

}
