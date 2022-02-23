package com.jvt.interior.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentProfileBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Profile extends Fragment {
    private FragmentProfileBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String uid;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static Fragment activeFragment;
    FirebaseAuth firebaseAuth;
    String profilePic, userName, userEmail, userMobile, userAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("User").child(uid).child("UserInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    profilePic = snapshot.child("profilePic").getValue().toString();
                    userEmail = snapshot.child("userEmail").getValue().toString();
                    userName = snapshot.child("userName").getValue().toString();
                    userMobile = snapshot.child("userMobile").getValue().toString();
                    userAddress = snapshot.child("userAddress").getValue().toString();
                    binding.name.setText(userName);
                    Glide.with(getContext()).load(profilePic).into(binding.image);
                    binding.address.setText(userAddress);
                    binding.email.setText(userEmail);
                    binding.mobile.setText(userMobile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.edit.setOnClickListener(view1 -> {
            Fragment fragment = new EditProfile();
            loadFragment(fragment,"EditProfile");
        });
        return view;
    }
    private void loadFragment(Fragment fragment, String tag) {
        executorService.execute(() -> {
            if (fragment != null) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment, tag).addToBackStack(tag).commit();

            }
            handler.post(() -> {
                activeFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
            });
        });
    }
}