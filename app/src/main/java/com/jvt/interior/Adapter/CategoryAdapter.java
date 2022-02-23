package com.jvt.interior.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jvt.interior.Common;
import com.jvt.interior.Model.CategoryModel;
import com.jvt.interior.R;
import com.jvt.interior.UI.ProductList;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    public Context context;
    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModelList.get(position);
        Glide.with(context).load(categoryModel.getItemImage()).into(holder.imageView);
        holder.name.setText(categoryModel.getItemName());
        holder.imageView.setOnClickListener(view -> {
            Common.itemNode = categoryModelList.get(position).getItemNode();
            Common.itemId = categoryModelList.get(position).getItemId();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment fragment = new ProductList();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }
    }
}
