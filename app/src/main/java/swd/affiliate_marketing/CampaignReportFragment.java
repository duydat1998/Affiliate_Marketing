package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.CampaignRegistration;


/**
 * A simple {@link Fragment} subclass.
 */
public class CampaignReportFragment extends Fragment {


    private Campaign campaign;
    private CampaignRegistration registration;

    private TextView tvCampaignName, tvAdvertiser, tvCampaignId, tvCampaignDate, tvPercentComission, tvRegisterDate, tvPromotionCode;
    private ImageView ivBanner;
    private Button btnDone;
    private TableLayout tblCampaignReport;
    public CampaignReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campaign_report, container, false);
        Bundle arguments = getArguments();

        campaign = (Campaign) arguments.getSerializable("campaign");
        registration = (CampaignRegistration) arguments.getSerializable("campaignRegistration");

        tvCampaignName = view.findViewById(R.id.tvCampaignName);
        tvAdvertiser = view.findViewById(R.id.tvAdvertiser);
        ivBanner = view.findViewById(R.id.ivBanner);
        tvCampaignId = view.findViewById(R.id.tvCampaignID);
        tvCampaignDate = view.findViewById(R.id.tvCampaignDate);
        tvPercentComission = view.findViewById(R.id.tvPercentComission);
        tvRegisterDate = view.findViewById(R.id.tvRegisterDate);
        tvPromotionCode = view.findViewById(R.id.tvPromotionCode);
        btnDone = view.findViewById(R.id.btnDone);
        tblCampaignReport = view.findViewById(R.id.tblCampaignReport);

        return view;
    }

    private void initializeData(){

    }

    private void initializeReportTable(){

    }
    

}
