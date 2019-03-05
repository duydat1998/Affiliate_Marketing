package swd.affiliate_marketing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import swd.affiliate_marketing.R;
import swd.affiliate_marketing.model.Campaign;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.CampaignItemViewHolder> {
    private List<Campaign> campaigns;
    private Context context;

    public CampaignAdapter(List<Campaign> campaigns, Context context){
        this.campaigns = campaigns;
        this.context = context;
    }

    @Override
    public CampaignItemViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_campaign, viewGroup, false);
        return new CampaignItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( CampaignItemViewHolder view, int i) {
        Campaign campaign = campaigns.get(i);
        Picasso.with(context)
                .load(campaign.banner).into(view.ivBanner);
        view.tvCampaignName.setText(campaign.campaignName);
        view.tvAdvertiserName.setText(campaign.advertiserID);
        view.tvTime.setText(campaign.startDate + " to " + campaign.endDate);
        view.tvCampaignId.setText(campaign.campaignID);
        view.tvState.setText("On going");
        view.tvState.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public static class CampaignItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCampaignName;
        public TextView tvAdvertiserName;
        public ImageView ivBanner;
        public TextView tvTime;
        public TextView tvState;
        private TextView tvCampaignId;

        public CampaignItemViewHolder(View itemView) {
            super(itemView);
            tvAdvertiserName =  itemView.findViewById(R.id.tvAdvertiserName);
            tvCampaignName =  itemView.findViewById(R.id.tvCampaignName);
            tvTime = itemView.findViewById(R.id.tvState);
            ivBanner = itemView.findViewById(R.id.ivBanner);
            tvState = itemView.findViewById(R.id.tvState);
            tvCampaignId = itemView.findViewById(R.id.tvCampaignID);
        }
    }
}
