package swd.affiliate_marketing.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swd.affiliate_marketing.CampaignReportFragment;
import swd.affiliate_marketing.R;
import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.CampaignRegistration;

public class RegisteredCampaignAdapter extends RecyclerView.Adapter<RegisteredCampaignAdapter.CampaignItemViewHolder>{

    private List<CampaignRegistration> campaignRegistrations;
    private Campaign campaign;
    private Context context;

    public RegisteredCampaignAdapter(List<CampaignRegistration> list, Context context){
        this.campaignRegistrations = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CampaignItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_registered_campaign, viewGroup, false);
        return new CampaignItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CampaignItemViewHolder view, int i) {
        final CampaignRegistration registration = campaignRegistrations.get(i);

        getCampaign(registration.campaignID);

        Picasso.with(context).load(campaign.banner).into(view.ivBanner);
        view.tvPromotionCode.setText("Promotion code: "+registration.promotionCode);
        view.tvState.setText("Register date: "+registration.registerDate);
        view.tvCampaignName.setText(campaign.campaignName);
        view.tvAdvertiserName.setText(campaign.advertiserID);
        view.tvTime.setText("From " +campaign.startDate + " to " + campaign.endDate);
        view.tvCampaignId.setText(campaign.campaignID);

        view.btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) context;
                CampaignReportFragment fragment = new CampaignReportFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("campaign", campaign);
                bundle.putSerializable("campaignRegistration", registration);

                fragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
    }

    public void getCampaign(String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type campaignType = Types.newParameterizedType(Campaign.class);
        final JsonAdapter<Campaign> jsonAdapter = moshi.adapter(campaignType);

        String domain = context.getResources().getString(R.string.virtual_api);

        String url = domain + "api/Campaigns/" + id;
        Request request = new Request.Builder().url(url).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Get API error: ", e.getMessage());
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(json != ""){
                    campaign = jsonAdapter.fromJson(json);
                }
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return campaignRegistrations.size();
    }

    public static class CampaignItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCampaignName;
        public TextView tvAdvertiserName;
        public ImageView ivBanner;
        public TextView tvTime;
        public TextView tvState;
        private TextView tvCampaignId;
        private TextView tvPromotionCode;
        private Button btnReport;

        public CampaignItemViewHolder(View itemView) {
            super(itemView);
            tvAdvertiserName =  itemView.findViewById(R.id.tvAdvertiserName);
            tvCampaignName =  itemView.findViewById(R.id.tvCampaignName);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivBanner = itemView.findViewById(R.id.ivBanner);
            tvState = itemView.findViewById(R.id.tvState);
            tvCampaignId = itemView.findViewById(R.id.tvCampaignID);
            tvPromotionCode = itemView.findViewById(R.id.tvPromotionCode);
            btnReport = itemView.findViewById(R.id.btnCampaignReport);
        }

    }
}
