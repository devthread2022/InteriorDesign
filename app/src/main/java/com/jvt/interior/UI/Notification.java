package com.jvt.interior.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.interior.Adapter.NotificationAdapter;
import com.jvt.interior.Model.NotificationModel;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentNotificationBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Notification extends Fragment {
    private FragmentNotificationBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String uid;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    List<NotificationModel> userNotifications = new ArrayList<>();
    NotificationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        uid = firebaseAuth.getCurrentUser().getUid();
        binding.itemRecycler.setHasFixedSize(true);
        binding.itemRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        databaseReference.child("User").child(uid).child("Notification")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot notificationSnap : snapshot.getChildren()) {
                                NotificationModel notifications=notificationSnap.getValue(NotificationModel.class);
                                userNotifications.add(notifications);
                            }

                            Collections.reverse(userNotifications);
                            adapter = new NotificationAdapter(userNotifications);
                            adapter.notifyDataSetChanged();
                            binding.itemRecycler.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return view;
    }
}