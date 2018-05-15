package com.example.ivanovnv.spacex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.ivanovnv.spacex.SpaceXAPI.APIutils;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Button mGetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetButton = findViewById(R.id.bt_get);

        mGetButton.setOnClickListener(v -> APIutils.getApi().getAllPastLaunches()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new ArrayList<Launch>(){{add(new Launch());}};
                })
                .subscribe(launches -> {
                    int i = 1;
                }));
    }
}
