package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swd.affiliate_marketing.global.GlobalVariable;
import swd.affiliate_marketing.model.CampaignRegistration;
import swd.affiliate_marketing.model.PromotionCodeTracking;
import swd.affiliate_marketing.model.Publisher;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportByMonthFragment extends Fragment {

    private HashMap<String, List<CampaignRegistration>> registrationHashMap = null;
    private List<CampaignRegistration> registrations;
    private double income;

    private static final String[] monthNames = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    private TableLayout tblReport;
    public ReportByMonthFragment() {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_by_month, container, false);
        tblReport = view.findViewById(R.id.tblReportByMonth);
        initializeData();
        initializeReportTable();
        return view;
    }

    private void initializeData(){
        getCampaignRegistrations();
        if(registrations != null){
            for(CampaignRegistration c : registrations){
                addToHashMap(c);
            }
        }
    }

    private void initializeReportTable(){
        addTableHeader("Month","Number of registered campaign", "Total amount earned");
        if(registrationHashMap.isEmpty()||registrationHashMap==null){
            addEmptyRow();
        } else {
            Iterator it = registrationHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                List<CampaignRegistration> cr = (List<CampaignRegistration>) pair.getValue();
                double totalIncome = 0;
                for(CampaignRegistration r : cr){
                    getIncome(r.promotionCode);
                    totalIncome += income;
                }
                addTableRow((String) pair.getKey(), cr.size()+"", totalIncome+"");
            }
        }
    }

    private void addTableHeader(String in1, String in2, String in3 ){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TextView col3 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
        col2.setLayoutParams(lp);
        col2.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
        col3.setLayoutParams(lp);
        col3.setPadding(10,1,10,1);


        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(in1);
        col2.setText(in2);
        col3.setText(in3);

        row.addView(col1);
        row.addView(col2);
        row.addView(col3);

        tblReport.addView(row);
    }

    private void addTableRow(String in1, String in2, String in3 ){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TextView col3 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());
        row.setBackgroundResource(R.drawable.row_border);

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f);
        col1.setLayoutParams(lp);
        col1.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
        col2.setLayoutParams(lp);
        col2.setPadding(10,1,10,1);

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
        col3.setLayoutParams(lp);
        col3.setPadding(10,1,10,1);


        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(in1);
        col2.setText(in2 +" campaign(s)");
        col3.setText(in3 + " VND");

        row.addView(col1);
        row.addView(col2);
        row.addView(col3);

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

    private void addToHashMap(CampaignRegistration registration){
        if(registrationHashMap == null){
            registrationHashMap = new HashMap<>();
        }

        String stringDate = registration.registerDate;
        stringDate = stringDate.substring(0,10);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = formatter.parse(stringDate);
        } catch (ParseException e){
            e.printStackTrace();
            date = null;
        }
        if(date != null){
            String month = getMonthName(date.getMonth());
            String year = (date.getYear() + 1900) +"";
            String time = month + " "+ year;
            if(registrationHashMap.containsKey(time)){
                registrationHashMap.get(time).add(registration);
            } else {
                List<CampaignRegistration> list = new ArrayList<>();
                list.add(registration);
                registrationHashMap.put(time, list);
            }

        }
    }

    private void getIncome(String code){
        OkHttpClient okHttpClient = new OkHttpClient();

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/PromotionCodes/"+code+"/Income";
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
                income = Double.parseDouble(json);
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
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


    private String getMonthName(int month){
        return monthNames[month];
    }
}
