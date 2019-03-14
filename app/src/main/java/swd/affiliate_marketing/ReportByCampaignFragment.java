package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportByCampaignFragment extends Fragment {


    private TableLayout tblReport;
    public ReportByCampaignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_by_campaign, container, false);
        tblReport = view.findViewById(R.id.tblReportByCampaign);
        initializeReportTable();
        return view;
    }

    private void initializeReportTable(){
        addTableHeader("No.","Campaign Name","Register date","Number of Orders", "Total amount earned","Detail");

    }

    private void addTableHeader(String in1, String in2, String in3, String in4, String in5, String in6 ){
        TextView col1 = new TextView(getActivity());
        TextView col2 = new TextView(getActivity());
        TextView col3 = new TextView(getActivity());
        TextView col4 = new TextView(getActivity());
        TextView col5 = new TextView(getActivity());
        TextView col6 = new TextView(getActivity());
        TableRow row = new TableRow(getActivity());

        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        lp.weight = 1; //column weight
        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.05f);
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

        lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.15f);
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

}
