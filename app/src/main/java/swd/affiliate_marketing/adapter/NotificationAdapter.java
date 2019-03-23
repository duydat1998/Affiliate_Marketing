package swd.affiliate_marketing.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import swd.affiliate_marketing.R;
import swd.affiliate_marketing.model.Notification;

public class NotificationAdapter extends BaseAdapter {
    private List<Notification> notifications;
    private Context context;
    public NotificationAdapter() {
        notifications = getNotificationsList();
    }

    public NotificationAdapter(Context context) {
        this.context = context;
        notifications = getNotificationsList();
    }


    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {

        return notifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notifications.indexOf(notifications.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_notification, parent, false);
        }

        TextView tvTime = convertView.findViewById(R.id.tvNotifyTime);
        TextView tvTitle = convertView.findViewById(R.id.tvNotifyTitle);
        TextView tvContent = convertView.findViewById(R.id.tvNotifyContent);
        Notification notification = notifications.get(position);
        tvTime.setText(notification.getTime());
        tvTitle.setText(notification.getTitle());
        tvContent.setText(notification.getContent());

        return convertView;
    }

    public List<Notification> getNotifications(){
        return notifications;
    }

    public List<Notification> getNotificationsList(){
        List<Notification> result = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences("swd.affiliate_marketing_preferences", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("notifyList",null);
        if(json != null ){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Notification>>(){}.getType();
            result = gson.fromJson(json, type);
        }
        return result;
    }

    public boolean addNotification(Notification notification){
        boolean result = false;
        notifications = getNotificationsList();
        if(notifications == null){
            notifications = new ArrayList<>();
        }
        notifications.add(notification);
        SharedPreferences sharedPreferences = context.getSharedPreferences("swd.affiliate_marketing_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notifications);
        editor.remove("notifyList");
        editor.putString("notifyList", json);
        result = editor.commit();
        return result;
    }
}
