package com.jvt.interior.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jvt.interior.Model.BookMarkedModel;
import com.jvt.interior.R;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    public Context context;
    private List<BookMarkedModel> bookMarkedModelList;

    public BookmarkAdapter(Context context, List<BookMarkedModel> bookMarkedModelList) {
        this.context = context;
        this.bookMarkedModelList = bookMarkedModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarked_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookMarkedModel bookMarkedModel = bookMarkedModelList.get(position);
        holder.name.setText(bookMarkedModel.getProductName());
        holder.description.setText(bookMarkedModel.getProductSummary());
        holder.rating.setText(String.valueOf(bookMarkedModel.getRating()));
        Glide.with(context).load(bookMarkedModel.getPicture()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bookMarkedModelList.size();
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
