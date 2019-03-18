package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
import swd.affiliate_marketing.adapter.RegisteredCampaignAdapter;
import swd.affiliate_marketing.global.GlobalVariable;
import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.CampaignRegistration;
import swd.affiliate_marketing.model.PromotionCodeTracking;
import swd.affiliate_marketing.model.Publisher;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportByCampaignFragment extends Fragment {


    private TableLayout tblReport;
    private List<CampaignRegistration> registrations;
    private List<PromotionCodeTracking> trackings;
    private double totalAmountEarned;
    public ReportByCampaignFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        Log.d("a","start");
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_by_campaign, container, false);
        tblReport = view.findViewById(R.id.tblReportByCampaign);
        totalAmountEarned = 0;
        initializeReportTable();
        return view;
    }

    private void getCampaignRegistrations(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type campaignType = Types.newParameterizedType(List.class, CampaignRegistration.class);
        final JsonAdapter<List<CampaignRegistration>> jsonAdapter = moshi.adapter(campaignType);

        String domain = getResources().getString(R.string.virtual_api);

        Publisher publisher = ((GlobalVariable) getActivity().getApplication()).publisher;
        String url = domain + "api/Publishers/"+publisher.publisherID+"/Campaigns";
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
                registrations = jsonAdapter.fromJson(json);
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getTrackings(String code){
        if(trackings != null){
            trackings.clear();
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type type = Types.newParameterizedType(List.class, PromotionCodeTracking.class);
        final JsonAdapter<List<PromotionCodeTracking>> jsonAdapter = moshi.adapter(type);

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/PromotionCodes/"+code+"/Trackings";
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
                trackings = jsonAdapter.fromJson(json);
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
        getCampaignRegistrations();
        addTableHeader("No.","Campaign Name","Register date","Number of Orders", "Total amount earned","Detail");
        if(registrations != null){
            if(registrations.size() > 0){
                for(int i = 0; i< registrations.size(); i++){
                    CampaignRegistration registration = registrations.get(i);
                    Campaign campaign = ((GlobalVariable) getActivity().getApplication()).getCampaignByID(registration.campaignID);
                    getTrackings(registration.promotionCode);
                    double total = 0;
                    for (PromotionCodeTracking t: trackings) {
                        total += t.moneyEarned;
                    }
                    addRow((i+1)+"", campaign.campaignName, registration.registerDate, trackings.size()+"",total+"VND", registration, campaign );
                    totalAmountEarned += total;
                }
            } else {
                addEmptyRow();
            }
        } else {
            addEmptyRow();
        }
        addTableFooter("Total Amount Earned", totalAmountEarned+"");
    }

    private void addTableHeader(String in1, String in2, String in3, String in4, String in5, String in6 ){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TextView col3 = new TextView(getActivity());
        TextView col4 = new TextView(getActivity());
        TextView col5 = new TextView(getActivity());
        TextView col6 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());
        row.setBackgroundResource(R.drawable.row_border);
        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.1f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.2f);
        col2.setLayoutParams(lp);
        col2.setPadding(10,1,10,1);

        col3.setLayoutParams(lp);
        col3.setPadding(10,1,10,1);

        col4.setLayoutParams(lp);
        col4.setPadding(10,1,10,1);

        col5.setLayoutParams(lp);
        col5.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.1f);
        col6.setLayoutParams(lp);
        col6.setPadding(10,1,10,1);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(in1);
        col2.setText(in2);
        col3.setText(in3);
        col4.setText(in4);
        col5.setText(in5);
        col6.setText(in6);

        row.addView(col1);
        row.addView(col2);
        row.addView(col3);
        row.addView(col4);
        row.addView(col5);
        row.addView(col6);

        tblReport.addView(row);
    }

    private void addRow(String in1, String in2, String in3, String in4, String in5, final CampaignRegistration registration, final Campaign campaign){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TextView col3 = new TextView(getActivity());
        TextView col4 = new TextView(getActivity());
        TextView col5 = new TextView(getActivity());
        Button col6 = new Button(getActivity());
        TableRow row = new TableRow(getActivity());
        row.setBackgroundResource(R.drawable.row_border);

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.1f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.2f);
        col2.setLayoutParams(lp);
        col2.setPadding(10,1,10,1);

        col3.setLayoutParams(lp);
        col3.setPadding(10,1,10,1);

        col4.setLayoutParams(lp);
        col4.setPadding(10,1,10,1);

        col5.setLayoutParams(lp);
        col5.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.1f);
        col6.setLayoutParams(lp);
        col6.setPadding(2,1,2,1);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(in1);
        col2.setText(in2);
        col3.setText(in3);
        col4.setText(in4);
        col5.setText(in5);

        col6.setText("View");
        col6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        col6.setTextColor(getResources().getColor(R.color.colorWhite));
        col6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CampaignReportFragment fragment = new CampaignReportFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("campaign", campaign);
                bundle.putSerializable("campaignRegistration", registration);

                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
        row.addView(col1);
        row.addView(col2);
        row.addView(col3);
        row.addView(col4);
        row.addView(col5);
        row.addView(col6);

        tblReport.addView(row);

    }

    private void addEmptyRow(){
        TextView col1 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());

        row.setBackgroundResource(R.drawable.row_border);
        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        col1.setText("No data found");

        row.addView(col1);
        tblReport.addView(row);
    }

    private void addTableFooter(String in1, String in2){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());

        row.setBackgroundResource(R.drawable.row_border);
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

        tblReport.addView(row);
    }
}
