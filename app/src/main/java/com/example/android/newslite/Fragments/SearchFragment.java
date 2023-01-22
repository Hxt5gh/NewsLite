package com.example.android.newslite.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


public class SearchFragment extends Fragment {
    private SearchView searchView;
    private String q_url = "https://newsapi.org/v2/everything?q=" +"cricket"  +"&apiKey=9473e83029a540c99eaae443015416e5";
    private RecyclerView recyclerView;
    private ArrayList<newsClass> list = new ArrayList<>();



    public SearchFragment() {
    }

    public SearchFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.idrecyclerview_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView = view.findViewById(R.id.idSearchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

//                https://newsapi.org/v2/top-headlines?q=sex&apiKey=9473e83029a540c99eaae443015416e5

                String url_search =   "https://newsapi.org/v2/top-headlines?q=" +query +"&apiKey=9473e83029a540c99eaae443015416e5";
                processing(url_search);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void processing( String url_url) {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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

                        if(title != "null" && description != "null" && content != "null" )
                        {
                            Log.d( "insertcheck" , description);
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