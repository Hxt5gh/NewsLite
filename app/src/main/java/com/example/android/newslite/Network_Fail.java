package com.example.android.newslite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Network_Fail extends AppCompatActivity {

    private  Button re_try;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_fail);
        re_try = findViewById(R.id.re_try);
        re_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetworkConnectivity() == true)
                {
                    Intent i = new Intent(Network_Fail.this , MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private boolean CheckNetworkConnectivity() {

        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if (info != null)
        {
            if(info.getType() == ConnectivityManager.TYPE_WIFI)
            {
                return true;
            }if(info.getType() == ConnectivityManager.TYPE_MOBILE)
        {
            return true;
        }

        }
        else
        {
            return false;
        }

        return false;
    }
}