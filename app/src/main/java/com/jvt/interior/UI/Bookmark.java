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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.interior.Adapter.BookmarkAdapter;
import com.jvt.interior.Adapter.ProductListAdapter;
import com.jvt.interior.Common;
import com.jvt.interior.Model.BookMarkedModel;
import com.jvt.interior.Model.ProductModel;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentBookmarkBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bookmark extends Fragment {
    private FragmentBookmarkBinding binding;
    private List<BookMarkedModel> productModelList = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static Fragment activeFragment;
    String uid;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        uid = firebaseAuth.getCurrentUser().getUid();
        binding.itemRecycler.setHasFixedSize(true);
        binding.itemRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        databaseReference.child("User").child(uid).child("Bookmarked").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    productModelList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        BookMarkedModel productModel = dataSnapshot.getValue(BookMarkedModel.class);
                        productModelList.add(productModel);
                    }
                    BookmarkAdapter productListAdapter = new BookmarkAdapter(getContext(),productModelList);
                    binding.itemRecycler.setAdapter(productListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.notification.setOnClickListener(view1 -> {
            Fragment fragment = new Notification();
            loadFragment(fragment,"Notification");
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