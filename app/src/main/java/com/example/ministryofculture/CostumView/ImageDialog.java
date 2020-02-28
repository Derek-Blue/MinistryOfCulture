package com.example.ministryofculture.CostumView;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.ministryofculture.R;

public class ImageDialog extends Dialog {

    Window window = null;
    ImageView imageView;
    private String imageUri;

    public ImageDialog(@NonNull Context context, String imageUri) {
        super(context);

        this.imageUri = imageUri;
        setwindow();
    }

    public ImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ImageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.imagedialog_view, null);
        imageView = view.findViewById(R.id.image);

        Glide.with(getContext())
                .load(imageUri)
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        setContentView(view);
        super.onCreate(savedInstanceState);
    }

    private void setwindow(){
        window = getWindow();
        window.setWindowAnimations(R.style.dialogAnim);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public void show() {
        setCanceledOnTouchOutside(true);
        super.show();
    }
}
