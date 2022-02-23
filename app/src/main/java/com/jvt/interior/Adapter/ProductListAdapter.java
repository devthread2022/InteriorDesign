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
import com.jvt.interior.Model.ProductModel;
import com.jvt.interior.R;
import com.jvt.interior.UI.ProductDetails;
import com.jvt.interior.UI.ProductList;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    public Context context;
    private List<ProductModel> productModelList;

    public ProductListAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);
        holder.name.setText(productModel.getProductName());
        holder.description.setText(productModel.getProductSummary());
        holder.rating.setText(String.valueOf(productModel.getRating()));
        Glide.with(context).load(productModel.getPicture()).into(holder.imageView);
        holder.itemView.setOnClickListener(view -> {
            Common.productId = productModelList.get(position).getProductId();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment fragment = new ProductDetails();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
