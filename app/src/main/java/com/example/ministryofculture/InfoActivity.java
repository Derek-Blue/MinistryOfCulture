package com.example.ministryofculture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ministryofculture.CostumView.ImageDialog;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class InfoActivity extends AppCompatActivity {

    ImageView info_image, info_image2, close;
    TextView casename, address, info;
    LinearLayout introView;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        introView = findViewById(R.id.introView);
        info_image = findViewById(R.id.info_image);
        info_image2 = findViewById(R.id.info_image2);
        casename = findViewById(R.id.name);
        address = findViewById(R.id.address);
        info = findViewById(R.id.info);
        info.setMovementMethod(ScrollingMovementMethod.getInstance());

        introView.getBackground().setAlpha(100);
        casename.getBackground().setAlpha(100);
        address.getBackground().setAlpha(100);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale014);

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                finish();
                overridePendingTransition(R.anim.no_anim,R.anim.fade_out_top);
            }
        });

        bundle = getIntent().getExtras();
        String caseName = bundle.getString("casename");
        String address_t = bundle.getString("address");
        final String imageUri = bundle.getString("imageUri");
        String intro = bundle.getString("intro");

        Glide.with(InfoActivity.this)
                .load(imageUri)
                .transform(new BlurTransformation(50,6))//霧化圖片
                .into(info_image);

        Glide.with(InfoActivity.this)
                .load(imageUri)
                .into(info_image2);

        casename.setText(caseName);
        address.setText(address_t);
        info.setText(intro);

        info_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialog imageDialog = new ImageDialog(InfoActivity.this, imageUri);
                imageDialog.show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        overridePendingTransition(R.anim.fade_out_top,R.anim.fade_out_top);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //沉浸式全畫面
        if (hasFocus){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    //View.SYSTEM_UI_FLAG_FULLSCREEN
                     View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}
