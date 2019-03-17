package swd.affiliate_marketing.global;

import android.app.Application;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swd.affiliate_marketing.MainActivity;
import swd.affiliate_marketing.R;
import swd.affiliate_marketing.adapter.CampaignAdapter;
import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.Publisher;

public class GlobalVariable extends Application {
    public Publisher publisher;
    private String advertiserName;
    private String campaignName;
    private Campaign campaign;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String randomPromotioncode(String input){
        //promotionCode has maximum 15 characters
        String promotionCode = "";
        if(input.length() < 15){
            promotionCode += input;
        } else {
            promotionCode += input.substring(0,5);
        }
        int other = 15 - promotionCode.length();
        for(int i=0; i<other; i++){
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            promotionCode += ALPHA_NUMERIC_STRING.charAt(character);
        }
        return promotionCode.toUpperCase();
    }

    public String getAdvertiserName(String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/Advertisers/Name/"+ id;
        Request request = new Request.Builder().url(url).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
                countDownLatch.countDown();
                advertiserName = "";
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                advertiserName = response.body().string();
                advertiserName = advertiserName.substring(1);
                advertiserName = advertiserName.substring(0, advertiserName.length()-1);
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
        return advertiserName;
    }

    public String getCampaignName(String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/Campaigns/Name/"+ id;
        Request request = new Request.Builder().url(url).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
                countDownLatch.countDown();
                campaignName = "";
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                campaignName = response.body().string();
                campaignName = campaignName.substring(1);
                campaignName = campaignName.substring(0, campaignName.length()-1);
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
        return campaignName;
    }

    public Campaign getCampaignByID(String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type campaignType = Types.newParameterizedType(Campaign.class);
        final JsonAdapter<Campaign> jsonAdapter = moshi.adapter(campaignType);

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/Campaigns/"+id;
        Request request = new Request.Builder().url(url).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
                countDownLatch.countDown();
                campaign = null;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                campaign = jsonAdapter.fromJson(json);
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
        return campaign;
    }
}
