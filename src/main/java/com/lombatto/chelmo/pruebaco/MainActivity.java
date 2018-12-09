package com.lombatto.chelmo.pruebaco;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    int currentPage=1;
    private static final int PAGESIZE = 10;
    List<ListaGit> listaGits=new ArrayList<ListaGit>();
    FloatingActionButton fab;
    FloatingActionButton fabizq;

    private TextView pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listview);
        setSupportActionBar(toolbar);
        pager=findViewById(R.id.pager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabizq = (FloatingActionButton) findViewById(R.id.fabizq);
        fabizq.setVisibility(View.GONE);

        new pedirLista().execute();

    }


    private class pedirLista extends AsyncTask<Void, Void, List<ListaGit>> {

        @Override
        protected List<ListaGit> doInBackground(Void... voids) {

            try {

                URL githubEndpoint = new URL("https://api.github.com/");

                HttpsURLConnection myCon = (HttpsURLConnection) githubEndpoint.openConnection();

                myCon.setRequestProperty("Accept", "application/vnd.github.v3+json");

                if (myCon.getResponseCode() == 200) {

                    InputStream response = myCon.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(response, "UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    jsonReader.beginObject(); // Start processing the JSON object

                    while (jsonReader.hasNext()) { // Loop through all keys
                        String key = jsonReader.nextName();

                        if (key.equals("public_gists_url")) {
                            String value = jsonReader.nextString();

                            URL listaGitHub = new URL(value);

                            HttpsURLConnection myCon2 = (HttpsURLConnection) listaGitHub.openConnection();

                            myCon2.setRequestProperty("Accept", "application/vnd.github.v3+json");
                            if (myCon2.getResponseCode() == 200) {
                                Log.e("MsjConnect","ok");
                                InputStream iSlistaGitHub = myCon2.getInputStream();
                                listaGits = readJsonStream(iSlistaGitHub);


                            }
                            myCon2.disconnect();

                            break; // Break out of the loop
                        } else {
                            jsonReader.skipValue(); // Skip values of other keys
                        }
                    }
                    jsonReader.close();
                } else {
                    Log.e("MsjErrorConexion", String.valueOf(myCon.getResponseCode()));
                }

                myCon.disconnect();

            } catch (Exception e) {
                Log.e("MsjErrorConexión_API", e.getLocalizedMessage());
            }



            return listaGits;

        }

        @Override
        protected void onPostExecute(final List<ListaGit> listaGits) {

            final int totalPage=listaGits.size()/PAGESIZE;
            addList(totalPage);
            Log.e("MsjTotalPage", String.valueOf(totalPage));


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    currentPage++;

                    if (totalPage>=currentPage) {
                        if (fabizq.getVisibility() == View.GONE) {
                            fabizq.setVisibility(View.VISIBLE);
                        }
                        if(currentPage==totalPage){
                            fab.setVisibility(View.GONE);
                        }
                        addList(totalPage);
                    }


                }
            });

            fabizq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    currentPage--;
                    Log.e("Msjcurrentpageonclick", String.valueOf(currentPage));
                    if(currentPage>=1) {
                        Log.e("MsjEntro","entrooo");
                        if (fab.getVisibility() == View.GONE) {
                            fab.setVisibility(View.VISIBLE);
                        }
                        if(currentPage==1){
                            fabizq.setVisibility(View.GONE);
                        }

                        addList(totalPage);
                    }



                }
            });
            super.onPostExecute(listaGits);
        }


    }

    public void addList(int totalPage) {
        Log.e("MsjCurrentPage", String.valueOf(currentPage));

        String pagerText = String.valueOf(currentPage) + " de " + String.valueOf(totalPage);

        pager.setText(pagerText);
        ArrayList<String> arrayList = new ArrayList<>();


        for(int i=(currentPage-1)*PAGESIZE;i<PAGESIZE*(currentPage);i++){
            arrayList.add(listaGits.get(i).getId());
            //Log.e("MsjContent",listaGits.get(i).getId());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int positionLista;
                    if(currentPage==1){
                        positionLista=0;
                    }else{
                        positionLista=(currentPage-1)*PAGESIZE;
                    }

                    Intent intent = new Intent(MainActivity.this,Content_git.class);
                    intent.putExtra("url",listaGits.get(position+positionLista).getUrl());
                    intent.putExtra("id",listaGits.get(position+positionLista).getId());
                    intent.putExtra("node_id",listaGits.get(position+positionLista).getNode_id());
                    intent.putExtra("html_url",listaGits.get(position+positionLista).getHtml_url());
                    intent.putExtra("state_public",listaGits.get(position+positionLista).isState_public());
                    intent.putExtra("created_at",listaGits.get(position+positionLista).getCreated_at());
                    intent.putExtra("updated_at",listaGits.get(position+positionLista).getUpdated_at());
                    intent.putExtra("description",listaGits.get(position+positionLista).getDescription());
                    intent.putExtra("ownerlogin",listaGits.get(position+positionLista).getOwner().getLogin());
                    intent.putExtra("owneravatar_url",listaGits.get(position+positionLista).getOwner().getAvatar_url());
                    intent.putExtra("ownerfollowers_url",listaGits.get(position+positionLista).getOwner().getFollowers_url());
                    intent.putExtra("ownerhtml_url",listaGits.get(position+positionLista).getOwner().getHtml_url());
                    intent.putExtra("ownerid",listaGits.get(position+positionLista).getOwner().getId());
                    intent.putExtra("owownerNode_idner",listaGits.get(position+positionLista).getOwner().getNode_id());
                    intent.putExtra("ownerRepos_url",listaGits.get(position+positionLista).getOwner().getRepos_url());


                    startActivity(intent);


                }catch (Exception e){
                    Log.e("MsjIntent",e.toString());
                }
            }
        });



    }
    public List<ListaGit> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readListaGitArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<ListaGit> readListaGitArray(JsonReader reader) throws IOException {
        List<ListaGit> listaGit = new ArrayList<ListaGit>();

        reader.beginArray();
        while (reader.hasNext()) {
            listaGit.add(readlistaGit(reader));
        }
        reader.endArray();
        return listaGit;
    }

    public ListaGit readlistaGit(JsonReader reader) throws IOException {

        String url=null;
        String id=null;
        String node_id=null;
        String html_url=null;
        //Files files=null;
        boolean state_public=false;
        String created_at=null;
        String updated_at=null;
        String description=null;
        Owner owner=null;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "url":
                    if(reader.peek() != JsonToken.NULL) {
                        url = reader.nextString();
                    }else{
                        url=null;
                    }
                    break;
                case "id":
                    if(reader.peek() != JsonToken.NULL) {
                        id = reader.nextString();
                    }else{
                        id=null;
                    }
                    break;
                case "node_id":
                    if(reader.peek() != JsonToken.NULL) {
                        node_id = reader.nextString();
                    }else{
                        node_id=null;
                    }
                    break;
                case "html_url":
                    if(reader.peek() != JsonToken.NULL) {
                        html_url = reader.nextString();
                    }else{
                        html_url=null;
                    }
                    break;
            /*case "files":
                files = readfiles(reader);
                break;
           */
                case "public":
                    if(reader.peek() != JsonToken.NULL) {
                        state_public = reader.nextBoolean();
                    }else{
                        state_public=false;
                    }
                    break;
                case "created_at":
                    if(reader.peek() != JsonToken.NULL) {
                        created_at = reader.nextString();
                    }else{
                        created_at=null;
                    }
                    break;
                case "updated_at":
                    if(reader.peek() != JsonToken.NULL) {
                        updated_at = reader.nextString();
                    }else{
                        updated_at=null;
                    }
                    break;
                case "description":
                    if(reader.peek() != JsonToken.NULL) {
                        description = reader.nextString();
                    }else{
                        description=null;
                    }
                    break;
                case "owner":
                    if(reader.peek() != JsonToken.NULL) {
                        owner = readOwner(reader);
                    }else{
                        owner=null;
                    }

                    break;

                default:
                    reader.skipValue();
                    break;

            }


        }

        reader.endObject();

        return new ListaGit(url, id, node_id, html_url,/*files,*/state_public,created_at,updated_at,description,owner);
    }

    /*public Files readFiles(JsonReader reader) throws IOException {
        String files_child=null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                username = reader.nextString();
            } else if (name.equals("followers_count")) {
                followersCount = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(username, followersCount);
    }
*/
    public Owner readOwner(JsonReader reader) throws IOException {
        String login=null;
        long id=-1;
        String node_id=null;
        String avatar_url=null;
        String html_url=null;
        String followers_url=null;
        String repos_url=null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "login":
                    if(reader.peek() != JsonToken.NULL) {
                        login = reader.nextString();
                    }else{
                        login=null;
                    }
                    break;
                case "id":
                    if(reader.peek() != JsonToken.NULL) {
                    id = reader.nextLong();
                    }else{
                        id=0;
                    }
                    break;
                case "node_id":
                    if(reader.peek() != JsonToken.NULL) {
                    node_id = reader.nextString();
                    }else{
                        node_id=null;
                    }
                    break;
                case "avatar_url":
                    if(reader.peek() != JsonToken.NULL) {
                    avatar_url = reader.nextString();
                    }else{
                        avatar_url=null;
                    }
                    break;
                case "html_url":
                    if(reader.peek() != JsonToken.NULL) {
                    html_url = reader.nextString();
                    }else{
                        html_url=null;
                    }
                    break;
                case "followers_url":
                    if(reader.peek() != JsonToken.NULL) {
                    followers_url = reader.nextString();
                    }else{
                        followers_url=null;
                    }
                    break;
                case "repos_url":
                    if(reader.peek() != JsonToken.NULL) {
                    repos_url = reader.nextString();
                    }else{
                        repos_url=null;
                    }
                    break;
                default:
                    reader.skipValue();
                    break;

            }

        }
        reader.endObject();
        return new Owner(login,id,node_id,avatar_url,html_url,followers_url,repos_url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
            final EditText edittext = new EditText(MainActivity.this);

            alert.setMessage("Escribe el nombre de usuario:")
                    .setView(edittext)
                    .setIcon(android.R.drawable.ic_menu_search)
                    .setTitle("Search")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String nombre_Search = edittext.getText().toString();

                            for(int j=0;j<listaGits.size();j++){
                                if(nombre_Search.toLowerCase().equals(listaGits.get(j).getOwner().getLogin().toLowerCase())){

                                }
                            }

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create()
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
