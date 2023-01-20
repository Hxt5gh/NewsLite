package com.example.android.newslite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!CheckNetworkConnectivity())
        {
            Intent i = new Intent(MainActivity.this , Network_Fail.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_main);
//        Log.d( "onResponse: ", "harry");


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // if (getSupportActionBar() != null)
       // {
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true); //dont usein main homescreen
      //  }


        recyclerView = findViewById(R.id.idRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        processing();


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

    private void processing() {

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest bbb = new JsonObjectRequest(Request.Method.GET, url_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String out = response.toString();
                Log.d("onResponse: " , out);
                try {
                    JSONArray articles = response.getJSONArray("articles");
                    for(int i = 0 ; i < articles.length(); i++)
                    {
                        JSONObject jsonObject = articles.getJSONObject(i);

                        JSONObject source = jsonObject.getJSONObject("source");

                        String id= source.getString("id");
                        String name= source.getString("name");

                        String author = jsonObject.getString("author");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String url = jsonObject.getString("url");
                        String urlToImage = jsonObject.getString("urlToImage");
                        String publishedAt = jsonObject.getString("publishedAt");
                        String content = jsonObject.getString("content");

                        list.add(new newsClass( new sourse(id , name) , author , title , description , url , urlToImage , publishedAt , content));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                customAdapter adapter = new customAdapter(MainActivity.this , list);
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0");
                return params;
            }
        };

        requestQueue.add(bbb);
    }


}