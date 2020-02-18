package com.example.ministryofculture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ministryofculture.Model.GetApi;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {

    ImageView info_image, info_image2, close;
    TextView casename, address, info;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        info_image = findViewById(R.id.info_image);
        info_image2 = findViewById(R.id.info_image2);
        casename = findViewById(R.id.name);
        address = findViewById(R.id.address);
        info = findViewById(R.id.info);
        info.setMovementMethod(ScrollingMovementMethod.getInstance());
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bundle = getIntent().getExtras();
        String caseName = bundle.getString("casename");
        String address_t = bundle.getString("address");
        String imageUri = bundle.getString("imageUri");
        String intro = bundle.getString("intro");

        Glide.with(InfoActivity.this)
                .load(imageUri)
                .transform(new BlurTransformation(50,6))
                .into(info_image);

        Glide.with(InfoActivity.this)
                .load(imageUri)
                .into(info_image2);

        casename.setText(caseName);
        address.setText(address_t);
        info.setText(intro);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //沉浸式全畫面
        if (hasFocus){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}
