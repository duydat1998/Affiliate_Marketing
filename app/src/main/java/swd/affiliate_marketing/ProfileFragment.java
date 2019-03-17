package swd.affiliate_marketing;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import swd.affiliate_marketing.global.GlobalVariable;
import swd.affiliate_marketing.model.Publisher;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private TextView tvUsername, tvName, tvPhone, tvEmail;
    private Button btnViewCampaign, btnGetReport, btnLogout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvUsername = view.findViewById(R.id.txtUsername);
        tvName = view.findViewById(R.id.txtName);
        tvPhone = view.findViewById(R.id.txtPhone);
        tvEmail = view.findViewById(R.id.txtEmail);
        btnViewCampaign = view.findViewById(R.id.btnViewRegisteredCampaign);
        btnGetReport = view.findViewById(R.id.btnGetReport);
        btnLogout = view.findViewById(R.id.btnLogout);

        Publisher publisher = ((GlobalVariable) getActivity().getApplication()).publisher;

        tvUsername.setText("Username: "+publisher.publisherID);
        tvName.setText("Name: "+publisher.firstname+ " " +publisher.lastname);
        tvPhone.setText("Phone number: "+ publisher.phone);
        tvEmail.setText("Email address: "+ publisher.email);
        btnViewCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new RegisteredCampaignFragment());
            }
        });

        btnGetReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new ReportFragment());
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("swd.affiliate_marketing_preferences", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", null);
                editor.putString("password", null);
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}
