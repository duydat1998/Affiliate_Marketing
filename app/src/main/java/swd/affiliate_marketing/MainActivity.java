package swd.affiliate_marketing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swd.affiliate_marketing.adapter.NotificationAdapter;
import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.CampaignRegistration;
import swd.affiliate_marketing.model.Notification;

public class MainActivity extends AppCompatActivity {

    private Campaign currentCampaign = null;
    private CampaignRegistration currentCampaignRegistration = null;

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

        String campaignID =  getIntent().getStringExtra("campaignID");
        Notification notification = (Notification) getIntent().getSerializableExtra("promotionCodeNotify");
        if(notification != null){
            NotificationAdapter adapter = new NotificationAdapter(this);
            adapter.addNotification(notification);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadFragment(new NotificationFragment());
                }
            });
        }
        if (campaignID != null) {
            getCampaignFromMessage(campaignID);
        } else {
            //loading the default fragment
            loadFragment(new CampaignFragment());
            currentCampaign = null;
        }
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            if (fragment instanceof CampaignFragment) {
                currentCampaign = null;
                currentCampaignRegistration = null;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void openCampaignDetailFragment(Campaign campaign) {
        CampaignDetailFragment detailFragment = new CampaignDetailFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .commit();
        currentCampaign = campaign;
    }

    public void openCampaignRegisterFragment(CampaignRegistration campaignRegistration) {
        CampaignRegisterFragment registerFragment = new CampaignRegisterFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, registerFragment)
                .commit();
        currentCampaignRegistration = campaignRegistration;
    }

    public Campaign getCurrentCampaign() {
        return currentCampaign;
    }

    public CampaignRegistration getCurrentCampaignRegistration() {
        return currentCampaignRegistration;
    }

    private void getCampaignFromMessage(final String campaignID) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type campaignType = Types.newParameterizedType(Campaign.class);
        final JsonAdapter<Campaign> jsonAdapter = moshi.adapter(campaignType);

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/Campaigns/" + campaignID;
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Campaign campaign = jsonAdapter.fromJson(json);
                openCampaignDetailFragment(campaign);
            }
        });
    }
}
