package com.jvt.interior.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jvt.interior.Common;
import com.jvt.interior.Model.ClassicModel;
import com.jvt.interior.R;
import com.jvt.interior.UI.ProductDetails;

import java.util.List;

public class ClassicAdapter extends RecyclerView.Adapter<ClassicAdapter.ViewHolder> {
    public Context context;
    private List<ClassicModel> classicModelList;

    public ClassicAdapter(Context context, List<ClassicModel> classicModelList) {
        this.context = context;
        this.classicModelList = classicModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classic_collection_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassicModel classicModel = classicModelList.get(position);
        Glide.with(context).load(classicModel.getItemImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(view -> {
            Common.productId = classicModelList.get(position).getItemId();
            Common.itemNode = classicModelList.get(position).getItemNode();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment fragment = new ProductDetails();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return classicModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
