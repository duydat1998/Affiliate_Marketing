package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import swd.affiliate_marketing.global.GlobalVariable;
import swd.affiliate_marketing.model.Publisher;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private TextView tvUsername, tvName, tvPhone, tvEmail;
    private Button btnViewCampaign, btnGetReport;

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

        Publisher publisher = ((GlobalVariable) getActivity().getApplication()).publisher;

        tvUsername.setText("Username: "+publisher.publisherID);
        tvName.setText("Name: "+publisher.firstname+publisher.lastname);
        tvPhone.setText("Phone number: "+ publisher.phone);
        tvEmail.setText("Email address: "+ publisher.email);
        btnViewCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnGetReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
