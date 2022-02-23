package com.jvt.interior.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.jvt.interior.Adapter.CategoryAdapter;
import com.jvt.interior.Adapter.ClassicAdapter;
import com.jvt.interior.Common;
import com.jvt.interior.Model.CategoryModel;
import com.jvt.interior.Model.ClassicModel;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dashboard extends Fragment {
    private FragmentDashboardBinding binding;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static Fragment activeFragment;
    DatabaseReference databaseReference, databaseReference1, databaseReference2,databaseReference3;
    String uid;
    FirebaseAuth firebaseAuth;
    private List<CategoryModel> categoryModelList = new ArrayList<>();
    private List<ClassicModel> classicModelList = new ArrayList<>();
    private List<ClassicModel> modernModelList = new ArrayList<>();
    String profilePic, userName, userEmail, userMobile, userAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        databaseReference2 = FirebaseDatabase.getInstance().getReference();
        databaseReference3 = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        binding.categoryRecycler.setHasFixedSize(true);
        binding.categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.classicRecycler.setHasFixedSize(true);
        binding.classicRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.modernRecycler.setHasFixedSize(true);
        binding.modernRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        loadCategory();
        loadClassic();
        loadModern();
        loadProfileInfo();
        binding.profile.setOnClickListener(view1 -> {
            Fragment profile = new Profile();
            loadFragment(profile,"Profile");
        });
        return view;
    }

    private void loadProfileInfo() {
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
                    Glide.with(getContext()).load(profilePic).into(binding.profile);
                    Common.imageUrl = profilePic;
                    Common.userName = userName;
                    Common.userMobile = userMobile;
                    Common.userEmail = userEmail;
                    Common.userAddress = userAddress;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadModern() {
        databaseReference1.child("Modern").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    modernModelList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ClassicModel classicModel = dataSnapshot.getValue(ClassicModel.class);
                        modernModelList.add(classicModel);
                    }
                    ClassicAdapter classicAdapter = new ClassicAdapter(getContext(),modernModelList);
                    binding.modernRecycler.setAdapter(classicAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadClassic() {
        databaseReference2.child("Classic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    classicModelList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ClassicModel classicModel = dataSnapshot.getValue(ClassicModel.class);
                        classicModelList.add(classicModel);
                    }
                    ClassicAdapter classicAdapter = new ClassicAdapter(getContext(),classicModelList);
                    binding.classicRecycler.setAdapter(classicAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCategory() {
        databaseReference3.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    categoryModelList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CategoryModel categoryModel = dataSnapshot.getValue(CategoryModel.class);
                        categoryModelList.add(categoryModel);
                    }
                    CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(),categoryModelList);
                    binding.categoryRecycler.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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