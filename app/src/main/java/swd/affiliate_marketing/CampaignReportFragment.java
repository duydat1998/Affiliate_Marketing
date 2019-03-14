package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import swd.affiliate_marketing.adapter.CampaignAdapter;
import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.CampaignRegistration;
import swd.affiliate_marketing.model.PromotionCodeTracking;


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

    private List<PromotionCodeTracking> trackings;
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

        initializeData();

        return view;
    }

    private void initializeData(){
        Picasso.with(getActivity()).load(campaign.banner).into(ivBanner);
        tvCampaignName.setText(campaign.campaignName + " Report");
        tvAdvertiser.setText("Advertiser: "+campaign.advertiserID);
        tvCampaignId.setText(campaign.campaignID);
        tvCampaignDate.setText("From "+campaign.startDate+" to "+campaign.endDate);
        tvPercentComission.setText("Comission: " + campaign.percentComission +"%");
        tvRegisterDate.setText("Registered in: "+registration.registerDate);
        tvPromotionCode.setText("Promotion code: "+ registration.promotionCode);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new CampaignFragment());
            }
        });
        getTrackings();
        initializeReportTable();
    }

    private void getTrackings(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type type = Types.newParameterizedType(List.class, PromotionCodeTracking.class);
        final JsonAdapter<List<PromotionCodeTracking>> jsonAdapter = moshi.adapter(type);

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/PromotionCodes/"+registration.promotionCode+"/Trackings";
        Request request = new Request.Builder().url(url).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                final List<PromotionCodeTracking> result = jsonAdapter.fromJson(json);
                trackings = result;
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initializeReportTable(){
        addTableHeader("No.","Time of use","Total of bill","Earned amount");
        double total = 0 ;
        if(trackings == null){
            addEmptyRow();
        } else {
            if(trackings.size() > 0){
                for(int i=0; i < trackings.size(); i++){
                    PromotionCodeTracking tracking = trackings.get(i);
                    addTableRow((i+1)+"", tracking.timeOfUsing, tracking.totalAmoutOfOrder+" VND", tracking.moneyEarned + " VND" );
                    total += tracking.moneyEarned;
                }
            } else {
                addEmptyRow();
            }
        }
        addTableFooter("Total", total+ " VND");
    }

    private void addTableHeader(String in1, String in2, String in3, String in4 ){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TextView col3 = new TextView(getActivity());
        TextView col4 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.1f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f);
        col2.setLayoutParams(lp);
        col2.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.2f);
        col3.setLayoutParams(lp);
        col3.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
        col4.setLayoutParams(lp);
        col4.setPadding(10,1,10,1);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(in1);
        col2.setText(in2);
        col3.setText(in3);
        col4.setText(in4);

        row.addView(col1);
        row.addView(col2);
        row.addView(col3);
        row.addView(col4);

        tblCampaignReport.addView(row);
    }

    private void addTableFooter(String in1, String in2){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.7f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
        col2.setLayoutParams(lp);
        col2.setPadding(10,1,10,1);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(in1);
        col2.setText(in2);

        row.addView(col1);
        row.addView(col2);

        tblCampaignReport.addView(row);
    }

    private void addTableRow(String in1, String in2, String in3, String in4){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TextView col3 = new TextView(getActivity());
        TextView col4 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.1f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f);
        col2.setLayoutParams(lp);
        col2.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.2f);
        col3.setLayoutParams(lp);
        col3.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
        col4.setLayoutParams(lp);
        col4.setPadding(10,1,10,1);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(in1);
        col2.setText(in2);
        col3.setText(in3);
        col4.setText(in4);

        row.addView(col1);
        row.addView(col2);
        row.addView(col3);
        row.addView(col4);
        tblCampaignReport.addView(row);
    }

    private void addEmptyRow(){
        TextView col1 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        col1.setText("No tracking found");

        row.addView(col1);
        tblCampaignReport.addView(row);
    }


}
