package com.lombatto.chelmo.pruebaco;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Content_git extends AppCompatActivity {

    private String url;
    private String id;
    private String node_id;
    private String html_url;
    private boolean isState_public;
    private String created_at;
    private String updated_at;
    private String description;
    private String ownerlogin;
    private String owneravatar_url;
    private String ownerfollowers_url;
    private String ownerhtml_url;
    private String ownerid;
    private String ownerNode_id;
    private String ownerRepos_url;

    private TextView tvurl;
    private TextView tvid;
    private TextView tvnode_id;
    private TextView tvhtml_url;
    private TextView tvistate_public;
    private TextView tvcreated_at;
    private TextView tvupdate_at;
    private TextView tvdescription;
    private TextView tvownerlogin;
    private TextView tvowneravatar_url;
    private TextView tvownerFollowers_url;
    private TextView tvownerHtml_url;
    private TextView tvownerId;
    private TextView tvownerNode_id;
    private TextView tvownerRepos_url;

    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_git);

        tvurl=findViewById(R.id.url);
        tvid=findViewById(R.id.id);
        tvnode_id=findViewById(R.id.node_id);
        tvhtml_url=findViewById(R.id.html_url);
        tvistate_public=findViewById(R.id.isState_public);
        tvcreated_at=findViewById(R.id.created_at);
        tvupdate_at=findViewById(R.id.updated_at);
        tvdescription=findViewById(R.id.description);
        tvownerlogin=findViewById(R.id.tvownerlogin);
        tvowneravatar_url=findViewById(R.id.tvowneravatar_url);
        tvownerFollowers_url=findViewById(R.id.tvownerFollowers_url);
        tvownerHtml_url=findViewById(R.id.tvownerHtml_url);
        tvownerId=findViewById(R.id.tvownerId);
        tvownerNode_id=findViewById(R.id.tvownerNode_id);
        tvownerRepos_url=findViewById(R.id.tvownerRepos_url);
        imageView=findViewById(R.id.image);

        try {
            url = getIntent().getStringExtra("url");
            id= getIntent().getStringExtra("id");
            node_id= getIntent().getStringExtra("node_id");
            html_url= getIntent().getStringExtra("html_url");
            isState_public= getIntent().getBooleanExtra("state_public",false);
            created_at= getIntent().getStringExtra("created_at");
            updated_at= getIntent().getStringExtra("updated_at");
            description= getIntent().getStringExtra("description");
            ownerlogin= getIntent().getStringExtra("ownerlogin");
            owneravatar_url= getIntent().getStringExtra("owneravatar_url");
            ownerfollowers_url= getIntent().getStringExtra("ownerfollowers_url");
            ownerhtml_url= getIntent().getStringExtra("ownerhtml_url");
            ownerid= getIntent().getStringExtra("ownerid");
            ownerNode_id= getIntent().getStringExtra("ownerNode_id");
            ownerRepos_url= getIntent().getStringExtra("ownerRepos_url");

            tvurl.setText(url);
            tvid.setText(id);
            tvnode_id.setText(node_id);
            tvhtml_url.setText(html_url);
            tvistate_public.setText(String.valueOf(isState_public));
            tvcreated_at.setText(created_at);
            tvupdate_at.setText(updated_at);
            tvdescription.setText(description);
            tvownerlogin.setText(ownerlogin);
            tvowneravatar_url.setText(owneravatar_url);


            new imageAvatar().execute();

            tvownerFollowers_url.setText(ownerfollowers_url);
            tvownerHtml_url.setText(ownerhtml_url);
            tvownerId.setText(ownerid);
            tvownerNode_id.setText(ownerNode_id);
            tvownerRepos_url.setText(ownerRepos_url);

        }catch (Exception e){
            Log.e("MsjErrorList",e.toString());
        }


    }

    private class imageAvatar extends AsyncTask<Void, Void, List<ListaGit>> {

        @Override
        protected List<ListaGit> doInBackground(Void... voids) {
            try {
                URL url = new URL(owneravatar_url);
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                imageView.setImageBitmap(image);
            }catch (Exception e){
                Log.e("MsjErrorAvatar",e.toString());
            }
            return null;
        }
    };

}
