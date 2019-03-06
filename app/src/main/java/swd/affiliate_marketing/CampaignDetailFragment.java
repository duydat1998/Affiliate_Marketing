package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import swd.affiliate_marketing.model.Campaign;


/**
 * A simple {@link Fragment} subclass.
 */
public class CampaignDetailFragment extends Fragment {


    private TextView tvCampaignName, tvAdvertiser, tvCampaignID, tvCampaignDate, tvPercentComission, tvCampaignContent;
    private ImageView ivBanner;
    private Campaign currentCampaign = null;
    private Button btnRegister;
    public CampaignDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_campaign_detail, container, false);

        currentCampaign = ((MainActivity) getActivity()).getCurrentCampaign();
        if(currentCampaign != null){
            tvCampaignName = view.findViewById(R.id.tvCampaignName);
            tvCampaignName.setText(currentCampaign.campaignName);

            tvCampaignID = view.findViewById(R.id.tvCampaignID);
            tvCampaignID.setText(currentCampaign.campaignID);

            tvAdvertiser = view.findViewById(R.id.tvAdvertiser);
            tvAdvertiser.setText("Advertiser: "+currentCampaign.advertiserID);

            tvCampaignDate = view.findViewById(R.id.tvCampaignDate);
            tvCampaignDate.setText("From "+currentCampaign.startDate + " to "+currentCampaign.endDate);

            tvPercentComission = view.findViewById(R.id.tvPercentComission);
            tvPercentComission.setText("Comission: "+currentCampaign.percentComission+" % for each bill");

            tvCampaignContent = view.findViewById(R.id.tvCampaignContent);
            tvCampaignContent.setText(currentCampaign.campaignContent);

            ivBanner = view.findViewById(R.id.ivBanner);
            Picasso.with(getContext()).load(currentCampaign.banner).into(ivBanner);

            btnRegister = view.findViewById(R.id.btnRegisterCampaign);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            Toast.makeText(getContext(), "There are something wrong.", Toast.LENGTH_SHORT).show();
        }
        return view;
    }


}
