package com.jvt.interior.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.interior.Adapter.CategoryAdapter;
import com.jvt.interior.Adapter.ListCategoryAdapter;
import com.jvt.interior.Model.CategoryModel;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class Category extends Fragment {
    private FragmentCategoryBinding binding;
    private List<CategoryModel> categoryModelList = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentCategoryBinding.inflate(inflater,container,false);
       View view = binding.getRoot();
       binding.itemRecycler.setHasFixedSize(true);
       binding.itemRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
       databaseReference.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    categoryModelList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CategoryModel categoryModel = dataSnapshot.getValue(CategoryModel.class);
                        categoryModelList.add(categoryModel);
                    }
                    ListCategoryAdapter categoryAdapter = new ListCategoryAdapter(getContext(),categoryModelList);
                    binding.itemRecycler.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       return view;
    }
}