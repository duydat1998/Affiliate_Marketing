package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swd.affiliate_marketing.adapter.CampaignAdapter;
import swd.affiliate_marketing.adapter.RegisteredCampaignAdapter;
import swd.affiliate_marketing.global.GlobalVariable;
import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.CampaignRegistration;
import swd.affiliate_marketing.model.Publisher;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredCampaignFragment extends Fragment {

    private RecyclerView rvCampaign;

    public RegisteredCampaignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_registered_campaign, container, false);
        rvCampaign = view.findViewById(R.id.rvCampaigns);
        rvCampaign.setLayoutManager(new GridLayoutManager(getContext(), 1));

        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type campaignType = Types.newParameterizedType(List.class, CampaignRegistration.class);
        final JsonAdapter<List<CampaignRegistration>> jsonAdapter = moshi.adapter(campaignType);

        String domain = getResources().getString(R.string.virtual_api);

        Publisher publisher = ((GlobalVariable) getActivity().getApplication()).publisher;
        String url = domain + "api/Publishers/"+publisher.publisherID+"/Campaigns";
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
                final List<CampaignRegistration> registrations = jsonAdapter.fromJson(json);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvCampaign.setAdapter(new RegisteredCampaignAdapter(registrations, getContext()));
                    }
                });
            }
        });
        return view;
    }

}
