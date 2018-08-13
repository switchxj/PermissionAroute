package com.example.administrator.permissionaroute.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.permissionaroute.R;
import com.qzzx.android.aroute.annotation.PRoute;
import com.qzzx.android.paroute.launcher.PAroute;


@PRoute(path = "test/main2activity")
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
