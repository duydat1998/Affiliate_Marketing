package swd.affiliate_marketing;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.CampaignRegistration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampaignRegisterFragment extends Fragment {

    private TextView tvCampaignResult, tvCampaignContent;
    private EditText tvPromotionCode;
    private Button btnCopyContent, btnCopyCode, btnDone;
    private Campaign currentCampaign;
    private CampaignRegistration currentCampaignRegistration;
    public CampaignRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_campaign_register, container, false);

        tvCampaignResult = view.findViewById(R.id.txtCampaignRegisterResult);
        tvCampaignContent = view.findViewById(R.id.txtCampaignContent);
        tvPromotionCode = view.findViewById(R.id.txtPromotionCode);
        btnCopyContent = view.findViewById(R.id.btnCopyContent);
        btnCopyCode = view.findViewById(R.id.btnCopyCode);
        btnDone = view.findViewById(R.id.btnDone);

        currentCampaign = ((MainActivity) getActivity()).getCurrentCampaign();
        currentCampaignRegistration = ((MainActivity) getActivity()).getCurrentCampaignRegistration();

        tvCampaignResult.setText("Campaign: "+currentCampaign.campaignName+" is registered successfully");
        tvCampaignContent.setText(currentCampaign.campaignContent);
        tvPromotionCode.setText(currentCampaignRegistration.promotionCode);

        btnCopyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tvPromotionCode.getText().toString();
                String message = copyToClipboard("promotion code", text);
                Toast.makeText(getActivity(),message , Toast.LENGTH_SHORT).show();
            }
        });
        btnCopyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tvCampaignContent.getText().toString();
                String message = copyToClipboard("promotion code", text);
                Toast.makeText(getActivity(),message , Toast.LENGTH_SHORT).show();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new CampaignFragment());
            }
        });
        return view;
    }

    private String copyToClipboard(String label,String text){
        String message;
        int sdk = android.os.Build.VERSION.SDK_INT;
        try{
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
                message = "Copied";
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(label,text);
                clipboard.setPrimaryClip(clip);
                message = "Copied";
            }
        } catch (Exception e){
            e.printStackTrace();
            message = "Copy FAILED";
        }
        return message;
    }

}
