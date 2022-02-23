package com.jvt.interior.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.jvt.interior.Model.NotificationModel;
import com.jvt.interior.R;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    List<NotificationModel> userNotificationList;

    public NotificationAdapter(List<NotificationModel> userNotificationList) {
        this.userNotificationList = userNotificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NotificationModel notificationModel = userNotificationList.get(position);
        holder.notificationHeading.setText(notificationModel.getNotificationHeading());
        holder.notificationBody.setText(notificationModel.getNotificationBody());
        holder.notificationDate.setText(notificationModel.getNotificationDate());


    }

    @Override
    public int getItemCount() {
        return userNotificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notificationBody,notificationDate,notificationHeading;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationDate = itemView.findViewById(R.id.notiDate);
            notificationBody = itemView.findViewById(R.id.notiContent);
            notificationHeading = itemView.findViewById(R.id.notiTitle);
        }
    }
}
