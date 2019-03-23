package swd.affiliate_marketing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import swd.affiliate_marketing.adapter.NotificationAdapter;
import swd.affiliate_marketing.model.Notification;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    private ListView lvNotification;
    private TextView tvNotifyEmpty;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        lvNotification = view.findViewById(R.id.lvNotification);
        tvNotifyEmpty = view.findViewById(R.id.tvNotifiEmpty);
        NotificationAdapter adapter = new NotificationAdapter(getContext());
        if(adapter.getNotifications()==null){
            tvNotifyEmpty.setText("No notification.");
            tvNotifyEmpty.setVisibility(View.VISIBLE);
        } else {
            if(adapter.getNotifications().isEmpty()){
                tvNotifyEmpty.setText("No notification.");
                tvNotifyEmpty.setVisibility(View.VISIBLE);
            } else {
                lvNotification.setAdapter(adapter);
                tvNotifyEmpty.setVisibility(View.INVISIBLE);
            }
        }

        return view;
    }

}
