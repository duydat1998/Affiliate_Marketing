package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {


    private FragmentTabHost tabHost;
    private Button btnDone;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report, container, false);
        tabHost = view.findViewById(R.id.fragment_tab_host);
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.tab_content);
        tabHost.addTab(tabHost.newTabSpec("reportByCampaign").setIndicator("Report by Campaign"), ReportByCampaignFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("reportByMonth").setIndicator("Report by Month"), ReportByMonthFragment.class, null);

        btnDone = view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new CampaignFragment());
            }
        });
        return view;
    }

}
