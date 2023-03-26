package com.example.android.newslite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.newslite.Fragments.HomeFragment;
import com.example.android.newslite.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String url = "https://api.github.com/users";
    private static final String url_url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=9473e83029a540c99eaae443015416e5";
    private ArrayList<newsClass> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ConnectivityManager connectivityManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //checking connectivity to internet
        if (!CheckNetworkConnectivity())
        {
            Intent i = new Intent(MainActivity.this , Network_Fail.class);
            startActivity(i);
        }

        //changing fragment home search
        bottomNavigationView = findViewById(R.id.idBottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.idHomeNav)
                {
                    FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.idframeLayout , new HomeFragment());
                    manager.commit();
                }
                else if(id == R.id.idSearchNav)
                {
                    FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
                    manager.replace(R.id.idframeLayout , new SearchFragment());
                    manager.commit();

                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.idHomeNav);







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