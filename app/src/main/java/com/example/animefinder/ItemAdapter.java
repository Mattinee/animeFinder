package com.example.animefinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<AnimeItem> mAnimeList;

    public ItemAdapter(Context context, ArrayList<AnimeItem> animeList) {
        mContext = context;
        mAnimeList = animeList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.result_object, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        AnimeItem currentItem = mAnimeList.get(position);

        String imageURL = currentItem.getImageURL();
        String title = currentItem.getTitle();

        holder.mTextViewTitle.setText(title);
        Picasso.get().load(imageURL).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mAnimeList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewTitle;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mTextViewTitle = itemView.findViewById(R.id.name);
        }
    }

}
