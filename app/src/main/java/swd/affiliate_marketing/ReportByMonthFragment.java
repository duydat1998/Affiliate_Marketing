package swd.affiliate_marketing;


import android.os.Bundle;
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
public class ReportByMonthFragment extends Fragment {


    private TableLayout tblReport;
    public ReportByMonthFragment() {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_by_month, container, false);
        tblReport = view.findViewById(R.id.tblReportByMonth);
        initializeReportTable();
        return view;
    }

    private void initializeReportTable(){
        addTableHeader("Month","Number of registered campaign", "Total amount earned");
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
}
