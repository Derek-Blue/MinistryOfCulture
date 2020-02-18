package com.example.ministryofculture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ministryofculture.Adapter.RemainAdapter;
import com.example.ministryofculture.Model.Api_post;
import com.example.ministryofculture.Model.GetApi;
import com.example.ministryofculture.Model.Remain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    APIservice apIservice;
    Call<List<GetApi>> call;

    private RadioGroup radiobox;
    private EditText search;
    private RecyclerView recyclerView;

    private List<Remain> remains, locationRemains, nameRemains;
    private RemainAdapter remainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radiobox = findViewById(R.id.radiobox);

        search = findViewById(R.id.search);
        search.clearFocus();
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        remains = new ArrayList<>();
        locationRemains = new ArrayList<>();
        nameRemains = new ArrayList<>();
        remainAdapter = new RemainAdapter(MainActivity.this, remains);
        recyclerView.setAdapter(remainAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cloud.culture.tw/frontsite/opendata/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apIservice = retrofit.create(APIservice.class);

        setRadioButton();
        getJson();

        radiobox.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = findViewById(group.getCheckedRadioButtonId());
                Log.d("Q200", ""+radioButton.getText().toString());
                String location = radioButton.getText().toString();
                searchLocation(location);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    searchName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void searchLocation(final String location) {

        locationRemains.clear();
        for (Remain remain : remains){
            if (remain.getAddress().substring(0,2).equals(location)){
                locationRemains.add(remain);
            }
        }
        remainAdapter.setRemains(locationRemains);
        remainAdapter.notifyDataSetChanged();
    }

    private void searchName(String name) {

        nameRemains.clear();

        if (locationRemains == null || locationRemains.size() == 0) {
            for (Remain remain : remains) {
                if (remain.getCasename().indexOf(name) != -1
                        || remain.getCasename().contains(name)) {
                    nameRemains.add(remain);
                }
            }
        }else {
            for (Remain remain : locationRemains) {
                if (remain.getCasename().indexOf(name) != -1
                        || remain.getCasename().contains(name)) {
                    nameRemains.add(remain);
                }
            }
        }
        remainAdapter.setRemains(nameRemains);
        remainAdapter.notifyDataSetChanged();
    }

    private void getJson() {

        call = apIservice.getPost();
        call.enqueue(new Callback<List<GetApi>>() {
            @Override
            public void onResponse(Call<List<GetApi>> call, Response<List<GetApi>> response) {
                if (!response.isSuccessful()){
                    Log.v("V30=","Code = "+ response.code());
                    return;
                }

                List<GetApi> getApis = response.body();

                remains.clear();
                assert getApis != null;
                for (GetApi getApi : getApis){
                    String imageUri = getApi.getRepresentImage();
                    String casename = getApi.getName();
                    String stylename = getApi.getBuildingYearName();
                    String city = getApi.getCityName();
                    String address = getApi.getAddress();
                    String intro = getApi.getIntro();

                    remains.add(new Remain(imageUri,casename,stylename, city+address, intro));
                }
                remainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GetApi>> call, Throwable t) {
                Log.v("V100=",""+t.getMessage());
            }
        });
    }

    private void setRadioButton(){

        call = apIservice.getPost();
        call.enqueue(new Callback<List<GetApi>>() {
            @Override
            public void onResponse(Call<List<GetApi>> call, Response<List<GetApi>> response) {
                if (!response.isSuccessful()){
                    Log.v("V30=","Code = "+ response.code());
                    return;
                }
                List<String> strings = new ArrayList<>();
                List<GetApi> getApis = response.body();

                assert getApis != null;
                for (GetApi getApi : getApis){
                    strings.add(getApi.getCityName().substring(0,2));
                }

                Set<String> stringSet = new HashSet<>();
                for (String s : strings){
                    stringSet.add(s);
                }
//                for (int i = 0; i<stringSet.size(); i++) {
//                    Log.d("Q200", "" + stringSet);
//                }
                for(String s : stringSet){
                    RadioButton radioButton = new RadioButton(MainActivity.this);
                    RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                            RadioGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(40,10,0,8);
                    radioButton.setLayoutParams(layoutParams);
                    radioButton.setText(s);
                    radioButton.setTextSize(18);
                    radioButton.setButtonDrawable(android.R.color.transparent);
                    radioButton.setPadding(20,5,20,5);
                    radioButton.setGravity(Gravity.CENTER);
                    radioButton.setBackground(getResources().getDrawable(R.drawable.radio_selector));
                    radiobox.addView(radioButton);
                }
            }

            @Override
            public void onFailure(Call<List<GetApi>> call, Throwable t) {
            Log.v("V100=",""+t.getMessage());
            }
        });
    }
}
