package com.example.android.newslite.HomeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.newslite.R;
import com.example.android.newslite.customAdapter;
import com.example.android.newslite.newsClass;
import com.example.android.newslite.sourse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CFragment extends Fragment {

    private RecyclerView recyclerView;
    private String url_technology = " https://newsapi.org/v2/top-headlines?country=in&category=Technology&apiKey=9473e83029a540c99eaae443015416e5";
    private ArrayList<newsClass> list = new ArrayList<>();
    public CFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.idRecycleViewC);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        processing(url_technology);
    }

    private void processing(String url_Health)  {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest bbb = new JsonObjectRequest(Request.Method.GET, url_Health, null, new Response.Listener<JSONObject>() {
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

                        if(title != "null" && description != "null" && content != "null" )
                        {
                            list.add(new newsClass( new sourse(id , name) , author , title , description , url , urlToImage , publishedAt , content));
                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                customAdapter adapter = new customAdapter(getContext() , list);
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