package com.example.ministryofculture.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.ministryofculture.InfoActivity;
import com.example.ministryofculture.Model.Remain;
import com.example.ministryofculture.R;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class RemainAdapter extends RecyclerView.Adapter<RemainAdapter.ViewHolder> {

    private Context context;
    private List<Remain> Remains;

    public RemainAdapter(Context context, List<Remain> Remains) {
        this.context = context;
        this.Remains = Remains;
    }

    public void setRemains(List<Remain> remains) {
        Remains = remains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new RemainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Remain remain = Remains.get(position);

        Glide.with(context.getApplicationContext())
                .load(remain.getImageUri())
                .transition(DrawableTransitionOptions.withCrossFade(500))//淡出效果 (使用後自動崁套顯示背景progress) 好用!!
                .into(holder.item_image);

        holder.casename.setText(remain.getCasename());

        if (TextUtils.isEmpty(remain.getStylename())){
            holder.stylename.setText("年分不詳");
        }else {
            holder.stylename.setText(remain.getStylename());
        }

        holder.address.setText(remain.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("casename", remain.getCasename());
                bundle.putString("address", holder.address.getText().toString());
                bundle.putString("imageUri", remain.getImageUri());
                bundle.putString("intro", remain.getIntro());
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.fade_in_top,R.anim.no_anim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Remains.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView casename, stylename, address;
        public ImageView item_image;
        public GifImageView progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            casename = itemView.findViewById(R.id.casename);
            stylename = itemView.findViewById(R.id.stylename);
            address = itemView.findViewById(R.id.address);
            item_image = itemView.findViewById(R.id.item_image);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}
