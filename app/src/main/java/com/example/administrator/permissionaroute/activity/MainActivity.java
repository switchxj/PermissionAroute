package com.example.administrator.permissionaroute.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.permissionaroute.R;
import com.qzzx.android.aroute.annotation.mode.PRoteMate;
import com.qzzx.android.paroute.appearance.template.IRouterRoot;
import com.qzzx.android.paroute.core.WareHouse;
import com.qzzx.android.paroute.launcher.PAroute;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PAroute.init(getApplication());
        TextView textView = (TextView) findViewById(R.id.tv);
        Log.i("asdasdasd", "---------------------");
        for (String src : WareHouse.IRouterRoots) {
            Log.i("asdasdasd", src);
            Map<String, PRoteMate> routes = new HashMap<>();
            try {
                Class aClass = Class.forName(src.replace(".class", ""));
                IRouterRoot iRouterRoot = (IRouterRoot) aClass.newInstance();

                iRouterRoot.loadInto(routes);


            } catch (Exception e) {
                e.printStackTrace();
            }

            textView.setText(src);

        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAroute.with("test/main2activity").sailing();
            }
        });
    }
}
