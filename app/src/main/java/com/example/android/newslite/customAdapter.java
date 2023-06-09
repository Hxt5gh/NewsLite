package com.example.android.newslite;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class customAdapter extends RecyclerView.Adapter<customAdapter.myView> {

    Context context;
    ArrayList<newsClass> list;

    public customAdapter(Context context, ArrayList<newsClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_layour, parent, false);
        return new myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {

        newsClass model = list.get(position);

        //titel
        holder.titel.setText(model.getTitle());


        //image

        String url = model.getUrlToImage();
            Glide.with(context).load(url).into(holder.image_View);
            Log.d("onBindViewHolder: ", url);


        //content

        holder.content.setText(model.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadActivity.class);
                intent.putExtra("titell", model.getTitle());
                intent.putExtra("image", model.getUrlToImage());
                intent.putExtra("discription", model.getDescription());
                intent.putExtra("Weburl", model.getUrl());
                String data = model.getContent();
                intent.putExtra("content", data);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myView extends RecyclerView.ViewHolder {
        ImageView image_View;
        TextView titel, content;

        public myView(@NonNull View itemView) {
            super(itemView);
            image_View = itemView.findViewById(R.id.imageView);
            titel = itemView.findViewById(R.id.textView);
            content = itemView.findViewById(R.id.textView2);
        }
    }
}
