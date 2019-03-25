package swd.affiliate_marketing;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import swd.affiliate_marketing.global.GlobalVariable;
import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.CampaignRegistration;
import swd.affiliate_marketing.model.PromotionCode;

public class CampaignDetailFragment extends Fragment {


    private TextView tvCampaignName, tvAdvertiser, tvCampaignID, tvCampaignDate, tvPercentComission, tvCampaignContent;
    private ImageView ivBanner;
    private Campaign currentCampaign = null;
    private Button btnRegister;
    private PromotionCode code;
    private Context context;
    public CampaignDetailFragment() {
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
            tvAdvertiser.setText("Advertiser: "+currentCampaign.advertiserName);

            tvCampaignDate = view.findViewById(R.id.tvCampaignDate);
            tvCampaignDate.setText("From "+currentCampaign.startDate.substring(0,10) + " to "+currentCampaign.endDate.substring(0,10));

            tvPercentComission = view.findViewById(R.id.tvPercentComission);
            tvPercentComission.setText("Comission: "+currentCampaign.percentComission+" % for each bill");

            tvCampaignContent = view.findViewById(R.id.tvCampaignContent);
            tvCampaignContent.setText(currentCampaign.campaignContent);

            ivBanner = view.findViewById(R.id.ivBanner);
            Picasso.with(getContext()).load(currentCampaign.banner).into(ivBanner);

            context = getActivity();
            btnRegister = view.findViewById(R.id.btnRegisterCampaign);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    code = null;
                    String publisherID = ((GlobalVariable) getActivity().getApplication()).publisher.publisherID;
                    createPromotionCode(publisherID);
                }
            });
        } else {
            Toast.makeText(getActivity(),"There are something wrong.", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void createPromotionCode(final String publisherID){
        //generate promotioncode has 15 characters
        String promotionCode = ((GlobalVariable) getActivity().getApplication()).randomPromotioncode(publisherID);
        Double percentEarn = currentCampaign.percentComission;
        //Create and register new PromotionCode

        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type type = Types.newParameterizedType(PromotionCode.class);
        final JsonAdapter<PromotionCode> jsonAdapter = moshi.adapter(type);

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/PromotionCodes";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("promotionCode1", promotionCode);
            jsonObject.put("percentEarn", percentEarn);
            jsonObject.put("isWorking",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Create code error: ", e.getMessage());
                    e.printStackTrace();
                    backgroundThreadShortToast(context, "Register FAIL");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String message = response.message();
                    if(message.contains("Conflict")){
                        backgroundThreadShortToast(context, "Register FAIL");
                    } else {
                        String json = response.body().string();
                        if(!json.isEmpty()){
                            code = jsonAdapter.fromJson(json);
                            registerCampaign(publisherID, code.promotionCode1, currentCampaign.campaignID);
                        } else {
                            backgroundThreadShortToast(context, "Register FAIL");
                        }
                    }
                }
            });
    }

    private void registerCampaign(String publisherID, String promotionCode, String campaignID){
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type type = Types.newParameterizedType(CampaignRegistration.class);
        final JsonAdapter<CampaignRegistration> jsonAdapter = moshi.adapter(type);

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/CampaignRegistrations";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("campaignID", campaignID);
            jsonObject.put("publisherID", publisherID);
            jsonObject.put("promotionCode", promotionCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Register error:", e.getMessage());
                backgroundThreadShortToast(context, "Register FAIL");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String message = response.message();
                if(message.contains("Conflict")){
                    backgroundThreadShortToast(context, "You have registered in this campaign");
                    deletePromotionCode(code.promotionCode1);
                } else {
                    String json = response.body().string();
                    if(!json.isEmpty()){
                        final CampaignRegistration registration = jsonAdapter.fromJson(json);
                        backgroundThreadShortToast(context, "Register SUCCESSFULLY");
                        ((MainActivity) getActivity()).openCampaignRegisterFragment(registration);
                    } else {
                        backgroundThreadShortToast(context, "Register FAIL");
                    }
                }
            }
        });
    }

    private void deletePromotionCode(String promotionCode){
        OkHttpClient okHttpClient = new OkHttpClient();

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/PromotionCodes/"+code.promotionCode1;
        Request request = new Request.Builder().url(url).delete().build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                backgroundThreadShortToast(context, "Network Error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static void backgroundThreadShortToast(final Context context,
                                                  final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
