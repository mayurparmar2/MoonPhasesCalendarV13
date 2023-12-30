package com.demo.moonphases.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.moonphases.R;
import com.jackandphantom.circularimageview.RoundedImage;

import com.demo.moonphases.OnClickInterface.OnItemClickListener;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.MyViewHolder> {
    public int[] dataSet;
    public Context mContext;
    private OnItemClickListener itemListener;


    public WallpaperAdapter(int[] iArr, Context context, OnItemClickListener onItemClickListener) {
        this.dataSet = iArr;
        this.mContext = context;
        this.itemListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wallpaper, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Glide.with(this.mContext).load(ContextCompat.getDrawable(this.mContext, this.dataSet[i])).into(myViewHolder.img_bg);
        if (i == 0) {
            myViewHolder.llDisplayBlank.setVisibility(View.GONE);
        } else if (i % 2 != 0) {
            myViewHolder.llDisplayBlank.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.llDisplayBlank.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.dataSet.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout btnSetWallpaper;
        RoundedImage img_bg;
        LinearLayout llDisplayBlank;
        LinearLayout loutMainTheme;

        public MyViewHolder(View view) {
            super(view);
            this.img_bg = (RoundedImage) view.findViewById(R.id.img_bg);
            this.loutMainTheme = (LinearLayout) view.findViewById(R.id.loutMainTheme);
            this.btnSetWallpaper = (LinearLayout) view.findViewById(R.id.btnSetWallpaper);
            this.llDisplayBlank = (LinearLayout) view.findViewById(R.id.llDisplayBlank);
            this.btnSetWallpaper.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            WallpaperAdapter.this.itemListener.OnClick(view, getLayoutPosition());
        }
    }
}
