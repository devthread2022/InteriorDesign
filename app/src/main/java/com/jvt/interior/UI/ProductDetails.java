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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.interior.Common;
import com.jvt.interior.Model.BookMarkedModel;
import com.jvt.interior.Model.SliderModel;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentProductDetailsBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductDetails extends Fragment {
    private FragmentProductDetailsBinding binding;
    DatabaseReference databaseReference;
    private ArrayList<SliderModel> sliderModelArrayList = new ArrayList<>();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static Fragment activeFragment;
    String picture, productName, productDescription, productSummary, productId, productCategory,name,description,rating1;
    Double rating;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Products").child(Common.itemNode).child(Common.productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    name = snapshot.child("productName").getValue().toString();
                    description = snapshot.child("productDescription").getValue().toString();
                    rating1 = snapshot.child("rating").getValue().toString();
                    picture = snapshot.child("picture").getValue().toString();
                    productSummary = snapshot.child("productSummary").getValue().toString();
                    productId = snapshot.child("productId").getValue().toString();
                    productCategory = snapshot.child("productCategory").getValue().toString();
                    binding.name.setText(name);
                    binding.rating.setText(rating1);
                    binding.description.setText(description);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        loadImages();
        binding.bookConsultation.setOnClickListener(view1 -> {
            Fragment fragment = new Appointment();
            loadFragment(fragment,"Appointment");
        });
        binding.bookmark.setOnClickListener(view1 -> {
            productName = name;
            productDescription = description;
            rating = Double.valueOf(rating1);
            BookMarkedModel bookMarkedModel = new BookMarkedModel(picture, productName, productDescription, productSummary, productId, productCategory,rating);
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
            databaseReference1.child("User").child(uid).child("Bookmarked").child(productId).setValue(bookMarkedModel);
            Toast.makeText(getContext(), "Added to bookmark..", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    private void loadImages() {
        DatabaseReference databaseReference20 = FirebaseDatabase.getInstance().getReference();
        databaseReference20.child("Products").child(Common.itemNode).child(Common.productId)
                .child("Images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    sliderModelArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        SliderModel sliderModel = dataSnapshot.getValue(SliderModel.class);
                        sliderModelArrayList.add(sliderModel);
                    }
                    for (int i = 0; i < sliderModelArrayList.size(); i++) {
                        String downloadImageUrl = sliderModelArrayList.get(i).getImageUrl();
                        ImageView imageView = new ImageView(getContext());
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(getContext()).load(downloadImageUrl).into(imageView);
                        binding.imageFlipper.addView(imageView);
                        binding.imageFlipper.setFlipInterval(2500);
                        binding.imageFlipper.setAutoStart(true);
                        binding.imageFlipper.startFlipping();
                        binding.imageFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
                        binding.imageFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);

                    }
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