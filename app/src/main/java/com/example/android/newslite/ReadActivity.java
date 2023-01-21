package com.example.android.newslite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ReadActivity extends AppCompatActivity {
    private ImageView image;
    private TextView titel , content;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        image = findViewById(R.id.fetchimage);
        titel = findViewById(R.id.fetchtitel);
        content = findViewById(R.id.fetchdiscription);

        Intent intent = getIntent();
        String titel_titel = intent.getStringExtra("titell");
        String image_url = intent.getStringExtra("image");
        String  Content_content = intent.getStringExtra("content");
        String  discription = intent.getStringExtra("discription");
        String url = intent.getStringExtra("Weburl");

        String real_news = discription +"\n\n" +Content_content;

        titel.setText(titel_titel);
        Glide.with(getApplicationContext()).load(image_url).into(image);
        content.setText(real_news);


        btn = findViewById(R.id.buttonWeb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                                    startActivity(intent);

            }
        });

    }
}